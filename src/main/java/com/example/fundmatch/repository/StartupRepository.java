package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Startup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartupRepository extends JpaRepository<Startup, Long> {
    boolean existsByCompanyName(String companyName);

}
