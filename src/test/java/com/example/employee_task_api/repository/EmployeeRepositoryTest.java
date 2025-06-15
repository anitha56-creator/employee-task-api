package com.example.employee_task_api.repository;

import com.example.employee_task_api.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Should save and retrieve employee by email")
    void testFindByEmail() {
        Employee emp = new Employee();
        emp.setId(6l);
        emp.setName("Alice");
        emp.setEmail("alice1@example.com");
        emp.setDepartment("HR");

        employeeRepository.save(emp);

        Optional<Employee> found = employeeRepository.findByEmail("alice1@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Alice");
    }

    @Test
    @DisplayName("Should return empty for non-existent email")
    void testFindByEmail_NotFound() {
        Optional<Employee> found = employeeRepository.findByEmail("nonexistent@example.com");
        assertThat(found).isNotPresent();
    }
}