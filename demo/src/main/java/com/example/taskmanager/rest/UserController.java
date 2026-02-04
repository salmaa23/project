package com.example.taskmanager.rest;

import com.example.taskmanager.dto.UserRequest;
import com.example.taskmanager.dto.UserResponse;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // ---------------- CREATE USER ----------------
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request); // all logic in service
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

        //TODO: any business logic should be in service layer, mapping included



    // ---------------- GET ALL USERS ----------------
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ---------------- GET USER BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // ---------------- UPDATE USER ----------------
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request
    ) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    // ---------------- GET ALL USERS WITH TASKS ----------------
    @GetMapping("/with-tasks")
    public ResponseEntity<List<UserResponse>> getAllUsersWithTasks() {
        List<UserResponse> users = userService.getAllUsersWithTasks();
        return ResponseEntity.ok(users);
    }


    // ---------------- DELETE USER ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
