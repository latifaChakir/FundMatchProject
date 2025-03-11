package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Sector;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    boolean existsByName(@NotBlank(message = "Sector name is required") @Size(max = 100, message = "Sector name cannot exceed 100 characters") String name);

    @Query("SELECT s.name, COUNT(st) FROM Sector s " +
            "JOIN s.startups st " +
            "GROUP BY s.name")
    List<Object[]> countStartupsBySector();

    @Query("SELECT s.name, COUNT(inv) FROM Sector s " +
            "JOIN s.investors inv " +
            "GROUP BY s.name")
    List<Object[]> countStartupsByInvestors();


}
