package com.example.taskmanager.rest;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
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



    //TODO: Filter params should be query parameters, not path segments --> STATUS

    // GET tasks by status
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) String status) {

        List<TaskResponse> tasks;

        if (status != null) {
            tasks = taskService.getTasksByStatus(status);
        } else {
            tasks = taskService.getAllTasks();
        }

        return ResponseEntity.ok(tasks);
    }

    // SEARCH by title
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchByTitle(@RequestParam String title) {
        List<TaskResponse> tasks = taskService.searchByTitle(title);
        return ResponseEntity.ok(tasks);
    }


    //TODO: Use @valid on request bodies, so validation annotations would work on request class

    // CREATE task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse response = TaskResponse.fromEntity(taskService.createTask(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= UPDATE =================

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @Valid @RequestBody TaskRequest request) {
        TaskResponse updated = taskService.updateTask(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}