package org.example.phongban.Controllers;



import jakarta.validation.Valid;

import org.example.phongban.Models.Dto.DepartmentDTO;
import org.example.phongban.Models.Entity.Department;
import org.example.phongban.Repositories.DepartmentRepository;
import org.example.phongban.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(
            DepartmentRepository departmentRepository
    ) {
        this.departmentRepository = departmentRepository;
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(
            @Valid @RequestBody DepartmentDTO departmentDTO
    ) {

        Department department = new Department();

        department.setName(departmentDTO.getName());
        department.setDescription(
                departmentDTO.getDescription()
        );

        Department savedDepartment =
                departmentRepository.save(department);

        ApiResponse<Department> response =
                new ApiResponse<>(
                        "SUCCESS",
                        "Tạo phòng ban thành công",
                        savedDepartment
                );

        return ResponseEntity.status(201).body(response);
    }
}