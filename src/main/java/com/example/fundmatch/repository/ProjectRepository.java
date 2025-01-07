package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
