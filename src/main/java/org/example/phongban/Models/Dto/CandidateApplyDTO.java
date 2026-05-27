package org.example.phongban.Models.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class CandidateApplyDTO {

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    private MultipartFile cvFile;

    public CandidateApplyDTO() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public MultipartFile getCvFile() {
        return cvFile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCvFile(MultipartFile cvFile) {
        this.cvFile = cvFile;
    }
}