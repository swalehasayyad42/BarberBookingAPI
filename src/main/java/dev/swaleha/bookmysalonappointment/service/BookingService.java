package dev.swaleha.bookmysalonappointment.service;

import dev.swaleha.bookmysalonappointment.entity.*;
import dev.swaleha.bookmysalonappointment.repository.*;
import dev.swaleha.bookmysalonappointment.entity.ShopService;
import dev.swaleha.bookmysalonappointment.modal.BookingRequest;
import dev.swaleha.bookmysalonappointment.modal.CustomerTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AvailabilityRepository availabilityRepository;
    private final ShopServiceRepository serviceRepository;
    @Autowired
    private final UserRepository userRepo;
    @Autowired
    private final ShopRepository shopRepo;

    public BookingService(BookingRepository bookingRepository,
                          AvailabilityRepository availabilityRepository,
                          ShopServiceRepository serviceRepository,
                          UserRepository userRepo,
                          ShopRepository shopRepo) {
        this.bookingRepository = bookingRepository;
        this.availabilityRepository = availabilityRepository;
        this.serviceRepository = serviceRepository;
        this.userRepo = userRepo;
        this.shopRepo = shopRepo;
    }

    // 1️⃣ Calculate available slots
    public List<LocalDateTime> calculateAvailableSlots(String barberId, LocalDate date, int totalDuration) {
        Availability availability = availabilityRepository.findByBarberId(barberId);
        if (availability == null) return Collections.emptyList();

        LocalTime start = LocalTime.parse(availability.getFromTime());
        LocalTime end = LocalTime.parse(availability.getToTime());

        LocalDateTime current = LocalDateTime.of(date, start);
        LocalDateTime endOfDay = LocalDateTime.of(date, end);

        List<Booking> bookings = bookingRepository.findByBarberIdAndStartTimeBetween(
            barberId, LocalDateTime.of(date, start), LocalDateTime.of(date, end)
        );

        List<LocalDateTime> slots = new ArrayList<>();
        while (!current.plusMinutes(totalDuration).isAfter(endOfDay)) {

            LocalDateTime slotStart = current; // ✅ final copy

            boolean overlap = bookings.stream().anyMatch(b ->
                slotStart.isBefore(b.getEndTime()) &&
                slotStart.plusMinutes(totalDuration).isAfter(b.getStartTime())
            );

            if (!overlap) {
                slots.add(slotStart);
            }

            current = current.plusMinutes(15);
        }
        return slots;
    }

    public Booking createBooking(
            String shopId,
            String barberId,
            String customerId,
            List<String> serviceIds,
            LocalDateTime startTime
    ) {

        // 🔥 IMPORTANT: fetch services from DB
        List<ShopService> services = serviceRepository.findAllById(serviceIds);

        if (services == null || services.isEmpty()) {
            throw new RuntimeException("No services found");
        }

        // Convert to ServiceItem
        List<ServiceItem> items = services.stream().map(s -> {
            ServiceItem item = new ServiceItem();
            item.setServiceId(s.getId());
            item.setName(s.getName());
            item.setRate(s.getRate());
            item.setDuration(s.getDuration());
            return item;
        }).toList();

        int totalDuration = services.stream()
                .mapToInt(ShopService::getDuration)
                .sum();

        LocalDateTime endTime = startTime.plusMinutes(totalDuration);

        int total = services.stream()
                .mapToInt(ShopService::getRate)
                .sum();

        Booking booking = new Booking();
        booking.setShopId(shopId);
        booking.setBarberId(barberId);
        booking.setCustomerId(customerId);
        booking.setServices(items);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setTotalAmount(java.math.BigDecimal.valueOf(total));
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    // 3️⃣ Daily collection
    public BigDecimal calculateDailyCollection(String barberId, LocalDate date) {
        List<Booking> completedBookings = bookingRepository.findByBarberIdAndStartTimeBetween(
            barberId,
            LocalDateTime.of(date, LocalTime.MIN),
            LocalDateTime.of(date, LocalTime.MAX)
        );

        return completedBookings.stream()
                                .filter(b -> BookingStatusHolder.Status.COMPLETED.equals(b.getStatus()))
                                .map(Booking::getTotalAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
 // BookingService.java
    public List<Booking> getBookingsByBarberAndDate(String barberId, LocalDateTime start, LocalDateTime end) {
        return bookingRepository.findByBarberIdAndStartTimeBetween(barberId, start, end);
    }
    
    public List<Booking> getBookingsByShopAndDate(String shopId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        // Assuming you have a BookingRepository with shopId and date filters
        return bookingRepository.findByShopIdAndStartTimeBetween(shopId, startOfDay, endOfDay);
    }
    
    public List<BookingRequest> getBookingsWithCustomerInfo(String shopId, String date) {

        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);

        List<Booking> bookings = bookingRepository
                .findByShopIdAndStartTimeBetween(shopId, startOfDay, endOfDay);
        for (Booking b : bookings) {
            System.out.println("Booking ID: " + b.getId()); // ✅ ADD THIS
        }

        List<BookingRequest> bookingTOs = new ArrayList<>();

        for (Booking b : bookings) {

            BookingRequest to = new BookingRequest();

            // ✅ FIXED
            to.setId(b.getId());
            to.setShopId(b.getShopId());
            to.setBarberId(b.getBarberId());

            to.setStatus(b.getStatus());
            to.setStartTime(b.getStartTime());
            to.setEndTime(b.getEndTime());
            to.setServices(b.getServices());
            to.setTotalAmount(b.getTotalAmount());

            // ✅ Fetch barber name
            User barber = userRepo.findById(b.getBarberId()).orElse(null);
            if (barber != null) {
                to.setBarberName(barber.getName());
            }

            // ✅ Fetch shop name (if you have shopRepo)
            Shop shop = shopRepo.findById(b.getShopId()).orElse(null);
            if (shop != null) {
                to.setShopName(shop.getShopName());
            }

            // ✅ Customer
            User customer = userRepo.findById(b.getCustomerId()).orElse(null);
            if (customer != null) {
                CustomerTO cto = new CustomerTO();
                cto.setId(customer.getId());
                cto.setName(customer.getName());
                to.setCustomer(cto);
            }

            bookingTOs.add(to);
        }

        return bookingTOs;
    }
    
    public ResponseEntity<?> cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            return ResponseEntity.badRequest().body("Booking not found");
        }

        // ⛔ Already completed
        if ("COMPLETED".equals(booking.getStatus())) {
            return ResponseEntity.badRequest().body("Cannot cancel completed booking");
        }

        // ⛔ Check 60 min rule
        LocalDateTime now = LocalDateTime.now();
        if (booking.getStartTime().minusMinutes(60).isBefore(now)) {
            return ResponseEntity.badRequest().body("Cannot cancel within 60 minutes");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking cancelled");
    }
    
    public List<BookingRequest> getBookingsWithDetails(String customerId) {

        List<Booking> bookings = bookingRepository.findByCustomerId(customerId);

        List<BookingRequest> result = new ArrayList<>();

        for (Booking b : bookings) {

        	BookingRequest to = new BookingRequest();
            to.setId(b.getId());
            to.setShopId(b.getShopId());
            to.setBarberId(b.getBarberId());
            to.setStatus(b.getStatus());
            to.setStartTime(b.getStartTime());
            to.setEndTime(b.getEndTime());
            to.setServices(b.getServices());

            // ✅ Shop name
            shopRepo.findById(b.getShopId()).ifPresent(shop -> {
                to.setShopName(shop.getShopName());
            });

            // ✅ Barber name
            userRepo.findById(b.getBarberId()).ifPresent(user -> {
                to.setBarberName(user.getName());
            });

            result.add(to);
        }

        return result;
    }
    
    public ResponseEntity<?> updateBookingStatus(String bookingId, String status) {

        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            return ResponseEntity.badRequest().body("Booking not found");
        }

        // Optional: prevent invalid transitions
        if ("CANCELLED".equals(booking.getStatus())) {
            return ResponseEntity.badRequest().body("Cannot update cancelled booking");
        }

        booking.setStatus(status.toUpperCase());
        booking.setUpdatedAt(LocalDateTime.now());

        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking updated successfully");
    }
}