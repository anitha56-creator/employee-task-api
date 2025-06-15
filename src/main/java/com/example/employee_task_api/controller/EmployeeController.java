package com.example.employee_task_api.controller;

import com.example.employee_task_api.dto.EmployeeRequestDTO;
import com.example.employee_task_api.dto.EmployeeResponseDTO;
import com.example.employee_task_api.dto.TaskResponseDTO;
import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody @Valid EmployeeRequestDTO dto) {
        return new ResponseEntity<>(service.createEmployee(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(@PathVariable Long id, @RequestParam(required = false) Optional<TaskStatus> status) {
        return ResponseEntity.ok(service.getTasks(id, status));
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> stats() {
        return ResponseEntity.ok(service.getTaskStats());
    }

}
