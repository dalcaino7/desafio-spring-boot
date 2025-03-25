package com.nuevospachile.gestion_tareas_api.repository;

import com.nuevospachile.gestion_tareas_api.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
}
