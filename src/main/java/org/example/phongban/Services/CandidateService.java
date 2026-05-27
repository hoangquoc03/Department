package org.example.phongban.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.transaction.Transactional;


import org.example.phongban.Exception.InvalidFileException;
import org.example.phongban.Models.Dto.CandidateApplyDTO;
import org.example.phongban.Models.Entity.Candidate;
import org.example.phongban.Repositories.CandidateRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final Cloudinary cloudinary;

    public CandidateService(
            CandidateRepository candidateRepository,
            Cloudinary cloudinary
    ) {
        this.candidateRepository = candidateRepository;
        this.cloudinary = cloudinary;
    }

    @Transactional
    public Candidate apply(
            CandidateApplyDTO dto
    ) throws IOException {

        MultipartFile file = dto.getCvFile();

        // 1. Validate file null
        if (file == null || file.isEmpty()) {

            throw new InvalidFileException(
                    "File CV không được để trống"
            );
        }

        // 2. Validate pdf
        String fileName =
                file.getOriginalFilename();

        String extension =
                fileName.substring(
                        fileName.lastIndexOf(".") + 1
                ).toLowerCase();

        if (!extension.equals("pdf")) {

            throw new InvalidFileException(
                    "Chỉ chấp nhận file PDF"
            );
        }

        // 3. Upload cloudinary
        Map uploadResult =
                cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                "resource_type",
                                "raw"
                        )
                );

        String fileUrl =
                uploadResult.get("secure_url")
                        .toString();


        Candidate candidate = new Candidate();

        candidate.setName(dto.getName());
        candidate.setEmail(dto.getEmail());
        candidate.setCvUrl(fileUrl);

        return candidateRepository.save(candidate);
    }
}