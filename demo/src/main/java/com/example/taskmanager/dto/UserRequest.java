package com.example.taskmanager.dto;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    private Set<String> roles = new HashSet<>();

    // Convert DTO -> User entity
    public User

    toEntity() {
        User user = new User(username, email, password);

        if (roles != null && !roles.isEmpty()) {
            user.setRoles(
                    roles.stream()
                            .map(Role::valueOf) // convert String -> Role
                            .collect(Collectors.toSet())
            );
        } else {
            user.setRoles(Set.of(Role.ROLE_USER)); // default role
        }

        return user;
    }
}