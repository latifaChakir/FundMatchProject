package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Sector;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    boolean existsByName(@NotBlank(message = "Sector name is required") @Size(max = 100, message = "Sector name cannot exceed 100 characters") String name);
}
