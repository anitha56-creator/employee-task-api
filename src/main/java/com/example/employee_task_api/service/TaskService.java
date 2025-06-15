package com.example.employee_task_api.service;

import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.dto.TaskRequestDTO;
import com.example.employee_task_api.dto.TaskResponseDTO;

public interface TaskService {
    TaskResponseDTO assignTaskToEmployee(TaskRequestDTO taskRequestDTO, Long employeeId);
    TaskResponseDTO updateTaskStatus(Long taskId, TaskStatus status);

}
