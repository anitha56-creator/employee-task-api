package com.example.employee_task_api.repository;

import com.example.employee_task_api.model.Task;
import com.example.employee_task_api.common.constants.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(Long employeeId);
    List<Task> findByAssignedToIdAndStatus(Long employeeId, TaskStatus status);
}