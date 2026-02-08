package com.example.taskmanager.config;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(
                    "admin",
                    "admin@example.com",
                    passwordEncoder.encode("admin123")
            );
            admin.addRole(Role.ROLE_ADMIN);
            admin.addRole(Role.ROLE_USER);
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User(
                    "user",
                    "user@example.com",
                    passwordEncoder.encode("user123")
            );
            user.addRole(Role.ROLE_USER);
            userRepository.save(user);
        }
    }
}
