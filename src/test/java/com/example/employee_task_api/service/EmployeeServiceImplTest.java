package com.example.employee_task_api.service;

import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.common.exception.DuplicateResourceException;
import com.example.employee_task_api.common.exception.ResourceNotFoundException;
import com.example.employee_task_api.dto.EmployeeRequestDTO;
import com.example.employee_task_api.dto.EmployeeResponseDTO;
import com.example.employee_task_api.model.Employee;
import com.example.employee_task_api.model.Task;
import com.example.employee_task_api.repository.EmployeeRepository;
import com.example.employee_task_api.repository.TaskRepository;
import com.example.employee_task_api.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceImplTest {
    private EmployeeRepository employeeRepo;
    private TaskRepository taskRepo;
    private ModelMapper modelMapper;
    private EmployeeServiceImpl service;
    @BeforeEach
    void setUp() {
        employeeRepo = mock(EmployeeRepository.class);
        taskRepo = mock(TaskRepository.class);
        modelMapper = new ModelMapper();
        service = new EmployeeServiceImpl(employeeRepo, taskRepo, modelMapper);
    }

    @Test
    void createEmployee_success() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setName("Test");
        dto.setEmail("test@example.com");
        dto.setDepartment("IT");

        when(employeeRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(employeeRepo.save(ArgumentMatchers.any(Employee.class))).thenAnswer(i -> {
            Employee e = i.getArgument(0);
            e.setId(1L);
            return e;
        });

        EmployeeResponseDTO response = service.createEmployee(dto);
        assertEquals("Test", response.getName());
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void createEmployee_duplicateEmail() {
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setName("Test");
        dto.setEmail("test@example.com");
        when(employeeRepo.findByEmail("test@example.com")).thenReturn(Optional.of(new Employee()));

        assertThrows(DuplicateResourceException.class, () -> service.createEmployee(dto));
    }

    @Test
    void getTasks_success() {
        Long empId = 1L;
        when(employeeRepo.existsById(empId)).thenReturn(true);
        List<Task> tasks = List.of(new Task(1L, "T1", "desc", TaskStatus.PENDING, null));
        when(taskRepo.findByAssignedToId(empId)).thenReturn(tasks);

        var result = service.getTasks(empId, Optional.empty());
        assertEquals(1, result.size());
        assertEquals("T1", result.get(0).getTitle());
    }

    @Test
    void getTasks_employeeNotFound() {
        when(employeeRepo.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.getTasks(99L, Optional.empty()));
    }

    @Test
    void getTaskStats_returnsStats() {
        Employee e = new Employee(1L, "Alice", "alice@example.com", "IT", new ArrayList<>());
        e.getTasks().add(new Task(1L, "T1", "desc", TaskStatus.COMPLETED, e));
        e.getTasks().add(new Task(2L, "T2", "desc", TaskStatus.PENDING, e));
        when(employeeRepo.findAll()).thenReturn(List.of(e));

        Map<String, Long> stats = service.getTaskStats();
        assertEquals(1L, stats.get("Alice"));
    }



}
