package com.nuevospachile.gestion_tareas_api.repository;

import com.nuevospachile.gestion_tareas_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
