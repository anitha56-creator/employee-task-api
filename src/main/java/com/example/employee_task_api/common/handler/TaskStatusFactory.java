package com.example.employee_task_api.common.handler;

import com.example.employee_task_api.common.constants.TaskStatus;

public class TaskStatusFactory {
    public static TaskStatusTransition getHandler(TaskStatus status) {
        return switch (status) {
            case PENDING -> new PendingStatusTransition();
            case IN_PROGRESS -> new InProgressStatusTransition();
            case COMPLETED -> new CompletedStatusTransition();
        };
    }
}
