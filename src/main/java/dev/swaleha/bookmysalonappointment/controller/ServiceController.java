package dev.swaleha.bookmysalonappointment.controller;


import dev.swaleha.bookmysalonappointment.modal.LoginRequest;
import dev.swaleha.bookmysalonappointment.modal.ServiceRequest;
import dev.swaleha.bookmysalonappointment.modal.ServiceTO;
import dev.swaleha.bookmysalonappointment.modal.UserTO;
import dev.swaleha.bookmysalonappointment.service.ShopServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {
    @Autowired
    private ShopServiceService serviceService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/byshopid")
    public ResponseEntity<List<ServiceTO>> getServicesByShopId(@RequestParam("shopId") String shopId) throws Exception {
        List<ServiceTO> services = serviceService.findByShopId(shopId);
        System.out.println(services);
        if (services.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(services);
    }

    @PostMapping("/addservice")
    public ResponseEntity<ServiceTO> save(@RequestBody ServiceRequest serviceRequest) throws Exception {
        ServiceTO serviceTO = serviceService.save(serviceRequest);
        return ResponseEntity.ok(serviceTO);

    }

    @PatchMapping("/updateprice")
    public ResponseEntity<ServiceTO> updateServicePrice(@RequestParam("serviceId") String serviceId, @RequestBody ServiceRequest serviceRequest) throws Exception {
        // Calling the service method to update the price
        ServiceTO updatedService = serviceService.updateServicePrice(serviceId, serviceRequest.getRate());

        if (updatedService == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // If the service is not found
        }

        return ResponseEntity.ok(updatedService);
    }
    
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<?> deleteService(@PathVariable String serviceId) {
        try {
        	serviceService.deleteService(serviceId);
            return ResponseEntity.ok("Service deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
