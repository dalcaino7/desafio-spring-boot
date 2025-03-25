package com.nuevospachile.gestion_tareas_api.controller;

import com.nuevospachile.gestiontareasapi.api.TasksApi;
import com.nuevospachile.gestiontareasapi.model.NewTask;
import com.nuevospachile.gestiontareasapi.model.TaskDTO;
import com.nuevospachile.gestion_tareas_api.entity.Task;
import com.nuevospachile.gestion_tareas_api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController implements TasksApi {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<List<TaskDTO>> tasksGet() {
        List<Task> tasks = taskService.findAllTasks();
        List<TaskDTO> dtos = tasks.stream().map(t -> {
            TaskDTO dto = new TaskDTO();
            dto.setId(t.getId().intValue());
            dto.setTitle(t.getTitle());
            dto.setDescription(t.getDescription());
            dto.setStatusId(t.getTaskStatus() != null ? t.getTaskStatus().getId().intValue() : null);
            dto.setAssignedUserId(t.getAssignedUser() != null ? t.getAssignedUser().getId().intValue() : null);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<Void> tasksPost(NewTask newTask) {
        Task t = new Task();
        t.setTitle(newTask.getTitle());
        t.setDescription(newTask.getDescription());
        taskService.createTask(t, newTask.getStatusId().longValue(), newTask.getAssignedUserId().longValue());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<TaskDTO> tasksTaskIdGet(Integer taskId) {
        Task existing = taskService.findTaskById(taskId.longValue());
        TaskDTO dto = new TaskDTO();
        dto.setId(existing.getId().intValue());
        dto.setTitle(existing.getTitle());
        dto.setDescription(existing.getDescription());
        dto.setStatusId(existing.getTaskStatus() != null ? existing.getTaskStatus().getId().intValue() : null);
        dto.setAssignedUserId(existing.getAssignedUser() != null ? existing.getAssignedUser().getId().intValue() : null);

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> tasksTaskIdPut(Integer taskId, NewTask newTask) {
        Task t = new Task();
        t.setTitle(newTask.getTitle());
        t.setDescription(newTask.getDescription());
        taskService.updateTask(taskId.longValue(), t, newTask.getStatusId().longValue(), newTask.getAssignedUserId().longValue());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> tasksTaskIdDelete(Integer taskId) {
        taskService.deleteTask(taskId.longValue());
        return ResponseEntity.noContent().build();
    }
}
