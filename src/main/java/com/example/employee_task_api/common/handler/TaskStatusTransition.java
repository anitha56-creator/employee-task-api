package com.example.employee_task_api.common.handler;


import com.example.employee_task_api.common.constants.TaskStatus;

public interface TaskStatusTransition {
    boolean canTransitionTo(TaskStatus newStatus);
    String getMessage();
}