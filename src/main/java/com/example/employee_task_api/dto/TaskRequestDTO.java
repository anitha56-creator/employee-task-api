package com.example.employee_task_api.dto;

import com.example.employee_task_api.common.constants.ErrorConstants;
import com.example.employee_task_api.common.constants.TaskStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskRequestDTO {
    @NotBlank(message = ErrorConstants.TITLE_MUST_NOT_BE_BLANK)
    private String title;
    private String description;
    public TaskStatus status;
}
