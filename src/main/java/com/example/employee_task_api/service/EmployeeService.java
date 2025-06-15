package com.example.employee_task_api.service;

import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.dto.EmployeeRequestDTO;
import com.example.employee_task_api.dto.EmployeeResponseDTO;
import com.example.employee_task_api.dto.TaskResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);
    List<TaskResponseDTO> getTasks(Long employeeId, Optional<TaskStatus> status);
    Map<String, Long> getTaskStats();

}
