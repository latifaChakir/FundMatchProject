package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector, Long> {
}
