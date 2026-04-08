package dev.swaleha.bookmysalonappointment.service;

import dev.swaleha.bookmysalonappointment.entity.Availability;
import dev.swaleha.bookmysalonappointment.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository repository;

    public Availability saveOrUpdateAvailability(Availability availability) {

        Availability existing = repository.findByBarberId(availability.getBarberId());

        if (existing != null) {
            // UPDATE
            existing.setFromTime(availability.getFromTime());
            existing.setToTime(availability.getToTime());
            existing.setShopId(availability.getShopId());

            return repository.save(existing);
        } else {
            // CREATE
            return repository.save(availability);
        }
    }

    public Availability getByBarberId(String barberId) {
        return repository.findByBarberId(barberId);
    }
}