package com.example.employee_task_api.controller;

import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.dto.TaskRequestDTO;
import com.example.employee_task_api.dto.TaskResponseDTO;
import com.example.employee_task_api.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAssignTaskToEmployee() throws Exception {
        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle("Task A");
        request.setDescription("Description A");
        request.setStatus(TaskStatus.PENDING);

        TaskResponseDTO response = new TaskResponseDTO(1L, "Task A", "Description A", TaskStatus.PENDING);

        Mockito.when(service.assignTaskToEmployee(any(TaskRequestDTO.class), eq(10L))).thenReturn(response);

        mockMvc.perform(post("/task/assign/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task A"))
                .andExpect(jsonPath("$.description").value("Description A"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void testUpdateTaskStatus() throws Exception {
        TaskResponseDTO response = new TaskResponseDTO(2L, "Task B", "Description B", TaskStatus.COMPLETED);

        Mockito.when(service.updateTaskStatus(2L, TaskStatus.COMPLETED)).thenReturn(response);

        mockMvc.perform(patch("/task/2/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Task B"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}
