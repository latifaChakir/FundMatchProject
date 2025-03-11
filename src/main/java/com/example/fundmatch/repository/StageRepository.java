package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long> {
    boolean existsByName(String name);

    @Query("SELECT s.name, COUNT(st) FROM Stage s " +
            "JOIN s.startups st " +
            "GROUP BY s.name")
    List<Object[]> countStartupsByStage();
}
