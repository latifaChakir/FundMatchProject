package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Startup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StartupRepository extends JpaRepository<Startup, Long> {
    boolean existsByCompanyName(String companyName);
    Optional<Startup> findByUserId(Long userId);
}
