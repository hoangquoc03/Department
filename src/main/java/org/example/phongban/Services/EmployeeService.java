package org.example.phongban.Services;

import org.example.phongban.Exception.DuplicateResourceException;
import org.example.phongban.Exception.ResourceNotFoundException;
import org.example.phongban.Models.Dto.EmployeeCreateDTO;
import org.example.phongban.Models.Entity.Department;
import org.example.phongban.Models.Entity.Employee;
import org.example.phongban.Repositories.DepartmentRepository;
import org.example.phongban.Repositories.EmployeeRepository;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Employee createEmployee(EmployeeCreateDTO dto) {

        // Kiểm tra email trùng
        if (employeeRepository.existsByEmail(dto.getEmail())) {

            throw new DuplicateResourceException(
                    "Email đã được sử dụng"
            );
        }

        // Kiểm tra phòng ban tồn tại
        Department department =
                departmentRepository.findById(dto.getDepartmentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Phòng ban không tồn tại"
                                ));

        // Tạo employee
        Employee employee = new Employee();

        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setSalary(dto.getSalary());

        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }
}