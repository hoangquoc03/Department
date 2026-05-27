package org.example.phongban.Controllers;



import jakarta.validation.Valid;

import org.example.phongban.Models.Dto.EmployeeCreateDTO;
import org.example.phongban.Models.Entity.Department;
import org.example.phongban.Models.Entity.Employee;
import org.example.phongban.Repositories.DepartmentRepository;
import org.example.phongban.Repositories.EmployeeRepository;
import org.example.phongban.Response.ApiResponse;
import org.example.phongban.Services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;
    public EmployeeController(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            EmployeeService employeeService
    ) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;

        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(
            @Valid @RequestBody EmployeeCreateDTO dto
    ) {

        Department department =
                departmentRepository.findById(dto.getDepartmentId())
                        .orElseThrow(() ->
                                new RuntimeException("Phòng ban không tồn tại"));

        Employee employee = new Employee();

        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setSalary(dto.getSalary());

        employee.setDepartment(department);

        Employee savedEmployee =
                employeeRepository.save(employee);

        ApiResponse<Employee> response =
                new ApiResponse<>(
                        "SUCCESS",
                        "Tạo nhân viên thành công",
                        savedEmployee
                );

        return ResponseEntity.status(201).body(response);
    }
    @PutMapping("/{id}/avatar")
    public ResponseEntity<?> uploadAvatar(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Employee employee =
                employeeService.uploadAvatar(id, file);

        ApiResponse<Employee> response =
                new ApiResponse<>(
                        "SUCCESS",
                        "Upload avatar thành công",
                        employee
                );

        return ResponseEntity.ok(response);
    }
}