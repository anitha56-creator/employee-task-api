package com.example.employee_task_api.common.handler;

import com.example.employee_task_api.common.constants.TaskStatus;

public class PendingStatusTransition implements TaskStatusTransition {
    public boolean canTransitionTo(TaskStatus newStatus) {
        return newStatus == TaskStatus.IN_PROGRESS || newStatus == TaskStatus.COMPLETED;
    }

    public String getMessage() {
        return "Task is pending.";
    }
}
