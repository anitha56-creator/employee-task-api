package com.example.employee_task_api.repository;

import com.example.employee_task_api.common.constants.TaskStatus;
import com.example.employee_task_api.model.Employee;
import com.example.employee_task_api.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testFindByAssignedToIdAndStatus() {
        Employee emp = new Employee();
        emp.setId(6l);
        emp.setName("Alice");
        emp.setEmail("alice1@example.com");
        emp.setDepartment("HR");
        employeeRepository.save(emp);
        Optional<Employee> found = employeeRepository.findByEmail("alice1@example.com");
        Task task1 = new Task(200l,"Fix bug", "Fix login bug", TaskStatus.PENDING, found.get());
        Task task2 = new Task(201l,"Write docs", "Write user guide", TaskStatus.COMPLETED, found.get());

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> pendingTasks = taskRepository.findByAssignedToIdAndStatus(found.get().getId(), TaskStatus.PENDING);
        assertThat(pendingTasks).hasSize(1);
        assertThat(pendingTasks.get(0).getTitle()).isEqualTo("Fix bug");
    }

    @Test
    void testFindByAssignedToId() {
        Employee emp = new Employee();
        emp.setId(6l);
        emp.setName("Alice");
        emp.setEmail("alice1@example.com");
        emp.setDepartment("HR");
        employeeRepository.save(emp);
        Optional<Employee> found = employeeRepository.findByEmail("alice1@example.com");
        Task task = new Task(202l,"Reply to ticket", "Customer issue", TaskStatus.IN_PROGRESS, found.get());
        taskRepository.save(task);

        List<Task> tasks = taskRepository.findByAssignedToId(found.get().getId());
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }
}