package org.example.phongban.Services;

import org.example.phongban.Exception.DuplicateResourceException;
import org.example.phongban.Exception.InvalidFileException;
import org.example.phongban.Exception.ResourceNotFoundException;
import org.example.phongban.Models.Dto.EmployeeCreateDTO;
import org.example.phongban.Models.Entity.Department;
import org.example.phongban.Models.Entity.Employee;
import org.example.phongban.Repositories.DepartmentRepository;
import org.example.phongban.Repositories.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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


    public Employee uploadAvatar(
            Long employeeId,
            MultipartFile file
    ) throws IOException {

        // 1. Kiểm tra employee tồn tại
        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Nhân viên không tồn tại"
                                ));

        // 2. Validate file rỗng
        if (file.isEmpty()) {

            throw new InvalidFileException(
                    "File không được để trống"
            );
        }

        // 3. Validate size < 2MB
        long maxSize = 2 * 1024 * 1024;

        if (file.getSize() > maxSize) {

            throw new InvalidFileException(
                    "Kích thước file vượt quá 2MB"
            );
        }

        // 4. Validate extension
        String fileName =
                file.getOriginalFilename();

        String extension =
                fileName.substring(
                        fileName.lastIndexOf(".") + 1
                ).toLowerCase();

        if (
                !extension.equals("jpg")
                        && !extension.equals("jpeg")
                        && !extension.equals("png")
        ) {

            throw new InvalidFileException(
                    "Định dạng file không hợp lệ"
            );
        }

        // 5. Tạo folder uploads nếu chưa có
        String uploadDir = "uploads/";

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 6. Rename file tránh trùng
        String newFileName =
                UUID.randomUUID()
                        + "." + extension;

        // 7. Save file
        File destination =
                new File(uploadDir + newFileName);

        file.transferTo(destination);

        // 8. Update DB
        employee.setAvatarUrl(
                "/uploads/" + newFileName
        );

        return employeeRepository.save(employee);
    }
}