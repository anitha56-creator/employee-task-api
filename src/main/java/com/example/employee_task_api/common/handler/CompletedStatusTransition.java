package com.example.employee_task_api.common.handler;

import com.example.employee_task_api.common.constants.TaskStatus;

public class CompletedStatusTransition implements TaskStatusTransition {
    public boolean canTransitionTo(TaskStatus newStatus) {
        return false; // cannot move further
    }

    public String getMessage() {
        return "Task is already completed.";
    }
}