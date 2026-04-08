package dev.swaleha.bookmysalonappointment.controller;

import dev.swaleha.bookmysalonappointment.entity.Shop;
import dev.swaleha.bookmysalonappointment.entity.User;
import dev.swaleha.bookmysalonappointment.modal.LoginRequest;
import dev.swaleha.bookmysalonappointment.modal.RegisterRequest;
import dev.swaleha.bookmysalonappointment.modal.UserResponseTO;
import dev.swaleha.bookmysalonappointment.modal.UserTO;
import dev.swaleha.bookmysalonappointment.service.ShopService;
import dev.swaleha.bookmysalonappointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private ShopService shopService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<UserTO>> getAllUsers() throws Exception {
        System.out.println("Received request for all users.");
        List<UserTO> users = userService.findAll();
        System.out.println(users);
        if (users.isEmpty()) {
            System.out.println("EMPTY************************");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<UserTO> login(@RequestBody LoginRequest loginRequest) throws Exception {
        System.out.println("Received login request: " + loginRequest);

        // Call the login method from UserService
        UserTO userTO = userService.login(loginRequest.getUserName(), loginRequest.getPassword());

        // If login is successful, return user details or a JWT token
        return ResponseEntity.ok(userTO);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        try {
            UserTO savedUser = userService.save(registerRequest);

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // Send error message if any validation fails
        }
    }

}
