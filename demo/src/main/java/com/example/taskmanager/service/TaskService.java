package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.model.*;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

    //TODO: Inconsistent exception strategy in services
    //  UserService throws EntityNotFoundException (JPA's). TaskService throws ResponseStatusException. Two different patterns for the same "not found" scenario across two services
    //  in the same app.
    // Use EntityNotFoundException for consistency

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // ================= GET =================

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::fromEntity) // ✅ fixed
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        Task task = getTaskOrThrow(id);
        return TaskResponse.fromEntity(task); // ✅ fixed
    }

    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskRepository.findByUser_Id(userId)
                .stream()
                .map(TaskResponse::fromEntity) // ✅ fixed
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByStatus(String status) {
        TaskStatus taskStatus = parseStatus(status);
        return taskRepository.findByStatus(taskStatus)
                .stream()
                .map(TaskResponse::fromEntity) // ✅ fixed
                .collect(Collectors.toList());
    }

    public List<TaskResponse> searchByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(TaskResponse::fromEntity) // ✅ fixed
                .collect(Collectors.toList());
    }

    // ================= CREATE =================

    public Task createTask(TaskRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.TODO); // default status
        task.setUser(user);

        return taskRepository.save(task);
    }

    // ================= UPDATE =================

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = getTaskOrThrow(id);
        User user = getUserOrThrow(request.getUserId());

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(parseStatus(request.getStatus()));
        task.setPriority(parsePriority(request.getPriority()));
        task.setUser(user);

        return TaskResponse.fromEntity(taskRepository.save(task)); // ✅ fixed
    }

    // ================= DELETE =================

    public void deleteTask(Long id) {
        Task task = getTaskOrThrow(id);
        taskRepository.delete(task);
    }

    // ================= HELPERS =================
    //TODO: throw task not found exception to use its handler method in GlobalExceptionHandler
    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private TaskStatus parseStatus(String status) {
        try {
            return TaskStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }
    }

    private TaskPriority parsePriority(String priority) {
        try {
            return TaskPriority.valueOf(priority.toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid priority");
        }
    }
}
