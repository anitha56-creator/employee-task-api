package com.example.employee_task_api.dto;

import com.example.employee_task_api.common.constants.ErrorConstants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmployeeRequestDTO {
    @NotBlank(message = ErrorConstants.NAME_MUST_NOT_BE_BLANK)
    private String name;
    @NotBlank(message = ErrorConstants.EMAIL_IS_REQUIRED)
    @Email(message = ErrorConstants.EMAIL_SHOULD_BE_VALID)
    private String email;
    private String department;
}
