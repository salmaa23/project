package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // ✅ Correct for ManyToOne User
    List<Task> findByUser_Id(Long userId);

    // TODO: Optional isn't semantically correct on a list, empty list is a vlid state
    // not used so just remove
    Optional<List<Task>> findAllByUser(User user);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String title);
}
