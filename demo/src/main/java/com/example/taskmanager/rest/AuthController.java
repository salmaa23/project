package com.example.taskmanager.rest;

import com.example.taskmanager.dto.LoginRequest;
import com.example.taskmanager.dto.LoginResponse;
import com.example.taskmanager.dto.UserRequest;
import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.security.CustomUserDetails;
import com.example.taskmanager.security.JwtUtil;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // ------------------ LOGIN ------------------
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails); // Use CustomUserDetails for login

        return new LoginResponse(token);
    }

    // ------------------ REGISTER ------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest request) {

        // 1. Check if username/email already exist
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username is already taken!"));
        }

        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is already in use!"));
        }

        // 2. Create user entity
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        // 3. Assign ROLE_USER by default
        user.addRole(Role.ROLE_USER);

        // 4. Save user
        userService.saveUser(user);

        // 5. Generate JWT token from username
        String token = jwtUtil.generateToken(user.getUsername());

        // 6. Return success + token
        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "token", token
        ));
    }
}
