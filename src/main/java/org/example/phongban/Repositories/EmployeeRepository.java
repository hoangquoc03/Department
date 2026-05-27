package org.example.phongban.Repositories;

import org.example.phongban.Models.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
}