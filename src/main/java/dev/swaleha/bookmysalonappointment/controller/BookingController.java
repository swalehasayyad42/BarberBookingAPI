package dev.swaleha.bookmysalonappointment.controller;

import dev.swaleha.bookmysalonappointment.entity.Booking;
import dev.swaleha.bookmysalonappointment.entity.ServiceItem;
import dev.swaleha.bookmysalonappointment.modal.BookingRequest;
import dev.swaleha.bookmysalonappointment.repository.BookingRepository;
import dev.swaleha.bookmysalonappointment.service.BookingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        
    }

    @GetMapping("/slots/{barberId}")
    public List<LocalDateTime> getAvailableSlots(
            @PathVariable String barberId,
            @RequestParam int totalDuration,
            @RequestParam String date
    ) {
        return bookingService.calculateAvailableSlots(barberId, LocalDate.parse(date), totalDuration);
    }

    @PostMapping("/create")
    public Booking createBooking(@RequestBody BookingRequest request) {
        return bookingService.createBooking(
                request.getShopId(),
                request.getBarberId(),
                request.getCustomerId(),
                request.getServiceIds(), // ✅ correct
                request.getStartTime()
        );
    }

    @GetMapping("/collection/{barberId}")
    public BigDecimal getDailyCollection(@PathVariable String barberId,
                                        @RequestParam String date) {
        return bookingService.calculateDailyCollection(barberId, LocalDate.parse(date));
    }
    
 // BookingController.java
    @GetMapping("/byBarberAndDate/{barberId}")
    public List<Booking> getBookingsByBarberAndDate(
            @PathVariable String barberId,
            @RequestParam String date
    ) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return bookingService.getBookingsByBarberAndDate(barberId, startOfDay, endOfDay);
    }
    
    @GetMapping("/byShopAndDate/{shopId}")
    public ResponseEntity<List<BookingRequest>> getBookingsByShopAndDate(
            @PathVariable String shopId,
            @RequestParam String date) {

        List<BookingRequest> bookings = bookingService.getBookingsWithCustomerInfo(shopId, date);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/myBookings/{customerId}")
    public List<BookingRequest> getMyBookings(@PathVariable String customerId) {
        return bookingService.getBookingsWithDetails(customerId);
    }
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable String bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBookingStatus(
            @PathVariable String bookingId,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");
        return bookingService.updateBookingStatus(bookingId, status);
    }
    
    
}