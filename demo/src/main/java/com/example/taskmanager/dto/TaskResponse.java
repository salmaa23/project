package com.example.taskmanager.dto;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.dto.TaskRequest;   // ✅ DTO for input
import com.example.taskmanager.dto.TaskResponse;  // ✅ DTO for output
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private String status;

    // Default constructor
    public TaskResponse() {}

    // Constructor with fields
    public TaskResponse(Long id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    // Convert Task entity to TaskResponse DTO
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus() != null ? task.getStatus().toString() : null
        );
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
