package com.shopcentral.backend.controller;

import com.shopcentral.backend.model.User;
import com.shopcentral.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (user.getName() == null ||
            user.getEmail() == null ||
            user.getPassword() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "All fields are required"));
        }

        Optional<User> existingUser =
                userService.loginUser(user.getEmail(), user.getPassword());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "User already exists"));
        }

        User savedUser = userService.registerUser(user);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Registration successful",
                        "user", savedUser.getEmail()
                )
        );
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Optional<User> loggedInUser =
                userService.loginUser(
                        user.getEmail(),
                        user.getPassword()
                );

        if (loggedInUser.isPresent()) {

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Login successful",
                            "user", loggedInUser.get().getEmail()
                    )
            );
        }

        return ResponseEntity.status(401)
                .body(Map.of(
                        "message",
                        "Invalid email or password"
                ));
    }
}