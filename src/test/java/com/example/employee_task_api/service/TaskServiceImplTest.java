package com.example.employee_task_api.service;

import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.common.exception.InvalidStatusTransitionException;
import com.example.employee_task_api.common.exception.ResourceNotFoundException;
import com.example.employee_task_api.dto.TaskRequestDTO;
import com.example.employee_task_api.model.Employee;
import com.example.employee_task_api.model.Task;
import com.example.employee_task_api.repository.EmployeeRepository;
import com.example.employee_task_api.repository.TaskRepository;
import com.example.employee_task_api.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private TaskRepository taskRepo;
    private EmployeeRepository employeeRepo;
    private ModelMapper modelMapper;
    private TaskServiceImpl service;

    @BeforeEach
    void setUp() {
        taskRepo = mock(TaskRepository.class);
        employeeRepo = mock(EmployeeRepository.class);
        modelMapper = new ModelMapper();
        service = new TaskServiceImpl(taskRepo, employeeRepo, modelMapper);
    }

    @Test
    void assignTaskToEmployee_success() {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTitle("Task");
        dto.setDescription("desc");
        dto.setStatus(TaskStatus.PENDING);

        Employee emp = new Employee(1L, "Test", "test@example.com", "IT", null);
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp));
        when(taskRepo.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        var response = service.assignTaskToEmployee(dto, 1L);
        assertEquals("Task", response.getTitle());
    }

    @Test
    void assignTaskToEmployee_employeeNotFound() {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTitle("Task");
        dto.setStatus(TaskStatus.PENDING);

        when(employeeRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.assignTaskToEmployee(dto, 99L));
    }

    @Test
    void updateTaskStatus_success() {
        Task task = new Task(1L, "Task", "desc", TaskStatus.PENDING, null);
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepo.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        var response = service.updateTaskStatus(1L, TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, response.getStatus());
    }

    @Test
    void updateTaskStatus_invalidTransition() {
        Task task = new Task(1L, "Task", "desc", TaskStatus.COMPLETED, null);
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(InvalidStatusTransitionException.class, () -> service.updateTaskStatus(1L, TaskStatus.PENDING));
    }

    @Test
    void updateTaskStatus_taskNotFound() {
        when(taskRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateTaskStatus(99L, TaskStatus.PENDING));
    }
}