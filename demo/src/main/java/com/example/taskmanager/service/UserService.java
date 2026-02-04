package com.example.taskmanager.service;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ---------------- GET ALL USERS ----------------
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ---------------- GET USER BY ID ----------------
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User with id " + id + " does not exist in the database"
                        )
                );
    }

    // ---------------- CREATE USER ----------------
    public User createUser(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
//            TODO: roles should be saved in an enum class and reused throughout
            user.setRoles(Set.of("ROLE_USER"));
        }

        return userRepository.save(user);
    }

    // ---------------- UPDATE USER ----------------
    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setRoles(updatedUser.getRoles());

        return userRepository.save(user);
    }

    // ---------------- DELETE USER ----------------
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
