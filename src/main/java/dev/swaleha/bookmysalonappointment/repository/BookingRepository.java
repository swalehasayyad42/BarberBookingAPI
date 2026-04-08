package dev.swaleha.bookmysalonappointment.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import dev.swaleha.bookmysalonappointment.entity.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {
    
    List<Booking> findByBarberIdAndStartTimeBetween(String barberId, LocalDateTime start, LocalDateTime end);
    
    List<Booking> findByShopIdAndStartTimeBetween(String shopId, LocalDateTime start, LocalDateTime end);
    
    List<Booking> findByCustomerId(String customerId);
}