/*package com.example.taskmanager.rest;

import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for registering users
 * Handles HTTP requests only

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    // Constructor injection
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST /register
     * Registers a new user
     * Returns the created user (with default roles applied)

    @PostMapping
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User createdUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
*/