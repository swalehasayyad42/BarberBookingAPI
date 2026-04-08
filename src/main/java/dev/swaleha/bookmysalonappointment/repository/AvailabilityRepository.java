package dev.swaleha.bookmysalonappointment.repository;

import dev.swaleha.bookmysalonappointment.entity.Availability;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvailabilityRepository extends MongoRepository<Availability, String> {

    Availability findByBarberId(String barberId);

}