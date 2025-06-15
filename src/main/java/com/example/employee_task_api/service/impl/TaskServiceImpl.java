package com.example.employee_task_api.service.impl;

import com.example.employee_task_api.common.exception.InvalidStatusTransitionException;
import com.example.employee_task_api.common.handler.TaskStatusTransition;
import com.example.employee_task_api.common.handler.TaskStatusFactory;
import com.example.employee_task_api.common.constants.ErrorConstants;
import com.example.employee_task_api.common.exception.ResourceNotFoundException;
import com.example.employee_task_api.dto.TaskRequestDTO;
import com.example.employee_task_api.dto.TaskResponseDTO;
import com.example.employee_task_api.model.Employee;
import com.example.employee_task_api.model.Task;
import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.repository.EmployeeRepository;
import com.example.employee_task_api.repository.TaskRepository;
import com.example.employee_task_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepo;
    private final EmployeeRepository employeeRepo;
    private final ModelMapper modelMapper;

    @Override
    public TaskResponseDTO assignTaskToEmployee(TaskRequestDTO taskRequestDTO, Long employeeId) {
        Task task = new Task(null, taskRequestDTO.getTitle(), taskRequestDTO.getDescription(), taskRequestDTO.getStatus(),null);
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));

        task.setAssignedTo(employee);
        taskRepo.save(task);
        return modelMapper.map(task, TaskResponseDTO.class);

    }
    @Override
    public TaskResponseDTO updateTaskStatus(Long taskId, TaskStatus status) {


        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.TASK_NOT_FOUND));
        TaskStatusTransition handler = TaskStatusFactory.getHandler(task.getStatus());
        if (!handler.canTransitionTo(status)) {
            throw new InvalidStatusTransitionException("Can't transition from " + task.getStatus()+" because " +handler.getMessage());
        }

        task.setStatus(status);
        taskRepo.save(task);
        return modelMapper.map(task, TaskResponseDTO.class);
    }


}
