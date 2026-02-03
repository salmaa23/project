package com.example.taskmanager;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskPriority;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	// Insert sample users and tasks
	@Bean
	CommandLineRunner initData(UserRepository userRepository, TaskRepository taskRepository) {
		return args -> {
			if (userRepository.count() == 0) {
				// Create users
				User alice = new User("alice", "alice@example.com", "password123");
				User bob = new User("bob", "bob@example.com", "password456");
				userRepository.save(alice);
				userRepository.save(bob);

				System.out.println("Sample users inserted!");

				// Create tasks for users
				Task task1 = new Task("Finish report", "Complete the monthly report", alice);
				task1.setStatus(TaskStatus.TODO);
				task1.setPriority(TaskPriority.HIGH);

				Task task2 = new Task("Plan meeting", "Schedule the team meeting", bob);
				task2.setStatus(TaskStatus.IN_PROGRESS);
				task2.setPriority(TaskPriority.MEDIUM);

				taskRepository.save(task1);
				taskRepository.save(task2);

				System.out.println("Sample tasks inserted!");
			}
		};
	}
}
