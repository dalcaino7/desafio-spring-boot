package com.nuevospachile.gestion_tareas_api.service;

import com.nuevospachile.gestion_tareas_api.entity.Task;
import com.nuevospachile.gestion_tareas_api.entity.TaskStatus;
import com.nuevospachile.gestion_tareas_api.entity.User;
import com.nuevospachile.gestion_tareas_api.repository.TaskRepository;
import com.nuevospachile.gestion_tareas_api.repository.TaskStatusRepository;
import com.nuevospachile.gestion_tareas_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final TaskStatusRepository statusRepo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository taskRepo,
                       TaskStatusRepository statusRepo,
                       UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.statusRepo = statusRepo;
        this.userRepo = userRepo;
    }

    public List<Task> findAllTasks() {
        return taskRepo.findAll();
    }

    public Task createTask(Task newTask, Long statusId, Long userId) {
        TaskStatus status = statusRepo.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        newTask.setTaskStatus(status);
        newTask.setAssignedUser(user);
        return taskRepo.save(newTask);
    }

    public Task findTaskById(Long id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public Task updateTask(Long id, Task incoming, Long statusId, Long userId) {
        Task existing = findTaskById(id);
        existing.setTitle(incoming.getTitle());
        existing.setDescription(incoming.getDescription());

        if (statusId != null) {
            TaskStatus status = statusRepo.findById(statusId)
                    .orElseThrow(() -> new IllegalArgumentException("Status not found"));
            existing.setTaskStatus(status);
        }
        if (userId != null) {
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            existing.setAssignedUser(user);
        }

        return taskRepo.save(existing);
    }

    public void deleteTask(Long id) {
        Task existing = findTaskById(id);
        taskRepo.delete(existing);
    }
}
