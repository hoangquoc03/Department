package org.example.phongban.Controllers;
import jakarta.validation.Valid;

import org.example.phongban.Models.Dto.CandidateApplyDTO;
import org.example.phongban.Models.Entity.Candidate;
import org.example.phongban.Response.ApiResponse;
import org.example.phongban.Services.CandidateService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(
            CandidateService candidateService
    ) {
        this.candidateService = candidateService;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> apply(
            @Valid @ModelAttribute CandidateApplyDTO dto
    ) throws IOException {

        Candidate candidate =
                candidateService.apply(dto);

        ApiResponse<Candidate> response =
                new ApiResponse<>(
                        "SUCCESS",
                        "Ứng tuyển thành công",
                        candidate
                );

        return ResponseEntity.ok(response);
    }
}