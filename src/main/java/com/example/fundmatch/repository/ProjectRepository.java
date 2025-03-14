package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStartupId(Long startupId);
}
