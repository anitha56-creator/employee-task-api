package com.example.employee_task_api.service.impl;

import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.common.exception.DuplicateResourceException;
import com.example.employee_task_api.common.constants.ErrorConstants;
import com.example.employee_task_api.common.exception.ResourceNotFoundException;
import com.example.employee_task_api.model.Task;
import com.example.employee_task_api.dto.EmployeeRequestDTO;
import com.example.employee_task_api.dto.EmployeeResponseDTO;
import com.example.employee_task_api.dto.TaskResponseDTO;
import com.example.employee_task_api.model.*;
import com.example.employee_task_api.repository.EmployeeRepository;
import com.example.employee_task_api.repository.TaskRepository;
import com.example.employee_task_api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final TaskRepository taskRepo;
    private final ModelMapper modelMapper;


    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        if (employeeRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException(ErrorConstants.EMAIL_ALREADY_EXISTS);
        }
        Employee employee = new Employee(null, dto.getName(), dto.getEmail(), dto.getDepartment(), new ArrayList<>());
        employee = employeeRepo.save(employee);
        return modelMapper.map(employee,EmployeeResponseDTO.class);
    }

    @Override
    public List<TaskResponseDTO> getTasks(Long employeeId, Optional<TaskStatus> status) {
        if (!employeeRepo.existsById(employeeId)) {
            throw new ResourceNotFoundException(ErrorConstants.EMPLOYEE_NOT_FOUND);
        }
        List<Task> taskList=status.map(s -> taskRepo.findByAssignedToIdAndStatus(employeeId, s))
                        .orElse(taskRepo.findByAssignedToId(employeeId));

        return taskList.stream().map(task->modelMapper.map(task, TaskResponseDTO.class)).collect(Collectors.toList());

    }

    @Override
    public Map<String, Long> getTaskStats() {
        return employeeRepo.findAll().stream().collect(Collectors.toMap(
                Employee::getName,
                emp -> emp.getTasks().stream().filter(t -> t.getStatus() == TaskStatus.COMPLETED).count()
        ));
    }


}
