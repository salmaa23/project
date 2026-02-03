package com.example.taskmanager.dto;

import com.example.taskmanager.model.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for sending User data to clients.
 * Includes tasks as TaskResponse DTOs.
 */
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
    private List<TaskResponse> tasks; // Include all tasks of this user

    public UserResponse() {}

    public UserResponse(Long id, String username, String email, Set<String> roles, List<TaskResponse> tasks) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.tasks = tasks;
    }

    /**
     * Static mapper from User entity to UserResponse DTO.
     * Maps all tasks using TaskResponse.fromEntity
     */
    public static UserResponse fromEntity(User user) {
        if (user == null) return null;

        List<TaskResponse> taskResponses = null;
        if (user.getTasks() != null) {
            taskResponses = user.getTasks()
                    .stream()
                    .map(TaskResponse::fromEntity) // ✅ Use static method here
                    .collect(Collectors.toList());
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                taskResponses
        );
    }


    // ---------------- Getters & Setters ----------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }

    public List<TaskResponse> getTasks() { return tasks; }
    public void setTasks(List<TaskResponse> tasks) { this.tasks = tasks; }
}
