package com.example.employee_task_api.controller;

import com.example.employee_task_api.dto.TaskRequestDTO;
import com.example.employee_task_api.dto.TaskResponseDTO;
import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;
    @PostMapping("/assign/{employeeId}")
    public ResponseEntity<TaskResponseDTO> assign(@PathVariable Long employeeId, @RequestBody @Valid TaskRequestDTO dto) {
        return new ResponseEntity<>(service.assignTaskToEmployee(dto,employeeId), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return ResponseEntity.ok(service.updateTaskStatus(id, status));
    }

}
