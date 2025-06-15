package com.example.employee_task_api.controller;

import com.example.employee_task_api.dto.EmployeeRequestDTO;
import com.example.employee_task_api.dto.EmployeeResponseDTO;
import com.example.employee_task_api.dto.TaskResponseDTO;
import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeRequestDTO request = new EmployeeRequestDTO();
        request.setName("John");
        request.setEmail("john@example.com");
        request.setDepartment("Engineering");

        EmployeeResponseDTO response = new EmployeeResponseDTO(1L, "John", "john@example.com", "Engineering");

        Mockito.when(service.createEmployee(ArgumentMatchers.any(EmployeeRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.department").value("Engineering"));
    }

    @Test
    void testGetTasksWithoutStatus() throws Exception {
        List<TaskResponseDTO> tasks = List.of(
                new TaskResponseDTO(1L, "Task 1", "Desc", TaskStatus.PENDING),
                new TaskResponseDTO(2L, "Task 2", "Desc", TaskStatus.IN_PROGRESS)
        );

        Mockito.when(service.getTasks(eq(1L), eq(Optional.empty()))).thenReturn(tasks);

        mockMvc.perform(get("/employees/1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    void testGetTasksWithStatus() throws Exception {
        List<TaskResponseDTO> tasks = List.of(
                new TaskResponseDTO(3L, "Task 3", "Done", TaskStatus.COMPLETED)
        );

        Mockito.when(service.getTasks(eq(1L), eq(Optional.of(TaskStatus.COMPLETED)))).thenReturn(tasks);

        mockMvc.perform(get("/employees/1/tasks")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("COMPLETED"));
    }

    @Test
    void testStats() throws Exception {
        Map<String, Long> stats = new HashMap<>();
        stats.put("John", 5L);
        stats.put("Alice", 2L);

        Mockito.when(service.getTaskStats()).thenReturn(stats);

        mockMvc.perform(get("/employees/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.John").value(5))
                .andExpect(jsonPath("$.Alice").value(2));
    }
}
