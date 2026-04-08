package dev.swaleha.bookmysalonappointment.controller;

import dev.swaleha.bookmysalonappointment.entity.Availability;
import dev.swaleha.bookmysalonappointment.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService service;

    @PostMapping
    public Availability saveAvailability(@RequestBody Availability availability) {
        return service.saveOrUpdateAvailability(availability);
    }

    @GetMapping("/barber/{barberId}")
    public ResponseEntity<?> getAvailability(@PathVariable String barberId) {
        Availability availability = service.getByBarberId(barberId);

        if (availability == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(availability);
    }
}