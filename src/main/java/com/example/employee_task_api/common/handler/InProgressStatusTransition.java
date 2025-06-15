package com.example.employee_task_api.common.handler;


import com.example.employee_task_api.common.constants.TaskStatus;

public class InProgressStatusTransition implements TaskStatusTransition {

    @Override
    public boolean canTransitionTo(TaskStatus newStatus) {
        return newStatus == TaskStatus.COMPLETED;
    }

    @Override
    public String getMessage() {
        return "Task is in progress.";
    }
}
