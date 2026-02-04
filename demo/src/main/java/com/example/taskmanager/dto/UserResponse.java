package com.example.taskmanager.dto;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private List<TaskResponse> tasks;

    public static UserResponse fromEntity(User user) {
        if (user == null) return null;

        List<TaskResponse> taskResponses = null;
        if (user.getTasks() != null) {
            taskResponses = user.getTasks()
                    .stream()
                    .map(TaskResponse::fromEntity)
                    .collect(Collectors.toList());
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .tasks(taskResponses)
                .build();
    }
}
