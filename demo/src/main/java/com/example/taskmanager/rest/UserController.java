package com.example.taskmanager.rest;

import com.example.taskmanager.dto.UserRequest;
import com.example.taskmanager.dto.UserResponse;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController

//TODO: Inconsistent base path conventions either make it all /api/****** like in TaskController

// TODO: Lombok inconsistency
// TaskController uses @RequiredArgsConstructor (Lombok). UserController and UserService use manual constructor injection.
// Use lombok throughout for less boilerplate

@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ---------------- CREATE USER ----------------
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request
    ) {

        //TODO: any business logic should be in service layer, mapping included

        // DTO -> Entity
        User user = request.toEntity();

        User savedUser = userService.createUser(user);

        // Entity -> DTO
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.fromEntity(savedUser));
    }

    // ---------------- GET ALL USERS ----------------
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    // ---------------- GET USER BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    // ---------------- UPDATE USER ----------------
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request
    ) {
        // DTO -> Entity
        User updatedUser = request.toEntity();

        User savedUser = userService.updateUser(id, updatedUser);

        return ResponseEntity.ok(UserResponse.fromEntity(savedUser));
    }

    @GetMapping("/with-tasks")
    public ResponseEntity<List<UserResponse>> getAllUsersWithTasks() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(UserResponse::fromEntity) // ✅ Use static mapper
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }


    // ---------------- DELETE USER ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
