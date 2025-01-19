package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {
    boolean existsByName(String name);
}
