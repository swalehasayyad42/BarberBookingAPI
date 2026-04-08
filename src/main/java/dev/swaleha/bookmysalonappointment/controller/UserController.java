package dev.swaleha.bookmysalonappointment.controller;


import dev.swaleha.bookmysalonappointment.modal.ServiceRequest;
import dev.swaleha.bookmysalonappointment.modal.ServiceTO;
import dev.swaleha.bookmysalonappointment.modal.UserTO;
import dev.swaleha.bookmysalonappointment.service.ShopServiceService;
import dev.swaleha.bookmysalonappointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/byshopid")
    public ResponseEntity<List<UserTO>> getUsersByShopId(@RequestParam("shopId") String shopId) throws Exception {
        List<UserTO> users = userService.findByShopId(shopId);
        System.out.println(users);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }
    
    @DeleteMapping("/barber/{barberId}")
    public ResponseEntity<?> deleteBarber(@PathVariable String barberId) {
        try {
            userService.deleteBarber(barberId);
            return ResponseEntity.ok("Barber deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
