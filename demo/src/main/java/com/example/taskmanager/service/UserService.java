package com.example.taskmanager.service;

import com.example.taskmanager.dto.UserRequest;
import com.example.taskmanager.dto.UserResponse;
import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ---------------- GET ALL USERS ----------------
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // ---------------- GET USER BY ID ----------------
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User with id " + id + " does not exist in the database"
                        )
                );
        return UserResponse.fromEntity(user);
    }

    // ---------------- CREATE USER ----------------
    public UserResponse createUser(UserRequest request) {
        User user = request.toEntity(); // convert DTO to entity

        // Ensure default role if none provided
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.ROLE_USER));
        }

        User savedUser = userRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    // ---------------- UPDATE USER ----------------
    public UserResponse updateUser(Long id, UserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User with id " + id + " does not exist in the database"
                        )
                );

        User updatedUser = request.toEntity(); // DTO -> entity
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        // Update roles properly
        if (updatedUser.getRoles() != null && !updatedUser.getRoles().isEmpty()) {
            existingUser.setRoles(updatedUser.getRoles());
        }

        User savedUser = userRepository.save(existingUser);
        return UserResponse.fromEntity(savedUser);
    }

    // ---------------- DELETE USER ----------------
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User with id " + id + " does not exist in the database"
                        )
                );
        userRepository.delete(user);
    }

    // ---------------- GET ALL USERS WITH TASKS ----------------
    public List<UserResponse> getAllUsersWithTasks() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity) // tasks are included in mapping
                .collect(Collectors.toList());
    }

    // ---------------- CHECK EXISTENCE ----------------
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // ---------------- SAVE USER DIRECTLY ----------------
    // Used by /auth/register
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // ---------------- FIND USER BY USERNAME ----------------
    // Used for login / JWT authentication
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username)
                );
    }
}
