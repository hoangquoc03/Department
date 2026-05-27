package org.example.phongban.Repositories;

import org.example.phongban.Models.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository
        extends JpaRepository<Candidate, Long> {
}