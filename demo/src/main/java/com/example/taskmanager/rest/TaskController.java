package com.example.taskmanager.rest;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskmanager.model.Task;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    // GET all tasks
    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    // GET task by id
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // GET tasks by user id
    @GetMapping("/user/{userId}")
    public List<TaskResponse> getTasksByUserId(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    // GET tasks by status
    @GetMapping("/status/{status}")
    public List<TaskResponse> getTasksByStatus(@PathVariable String status) {
        return taskService.getTasksByStatus(status);
    }

    // SEARCH by title
    @GetMapping("/search")
    public List<TaskResponse> searchByTitle(@RequestParam String title) {
        return taskService.searchByTitle(title);
    }


    // CREATE task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        Task task = taskService.createTask(request);           // save entity
        TaskResponse response = TaskResponse.fromEntity(task); // convert to DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // UPDATE task
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,
                                   @RequestBody TaskRequest request) {
        return taskService.updateTask(id, request);
    }

    // DELETE task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
