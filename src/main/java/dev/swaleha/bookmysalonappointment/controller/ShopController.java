package dev.swaleha.bookmysalonappointment.controller;

import dev.swaleha.bookmysalonappointment.modal.ShopTO;
import dev.swaleha.bookmysalonappointment.modal.UserTO;
import dev.swaleha.bookmysalonappointment.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops")
@CrossOrigin(origins = "http://localhost:3000")
public class ShopController {

    @Autowired
    ShopService shopService;
    @GetMapping("/byshopname")
    public ResponseEntity<List<ShopTO>> getBarbersByShopName(@RequestParam("shopName") String shopName) throws Exception {
        List<ShopTO> users = shopService.findByShopName(shopName);
        System.out.println(users);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }
}
