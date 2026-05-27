package org.example.phongban.Repositories;


import org.example.phongban.Models.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {
}