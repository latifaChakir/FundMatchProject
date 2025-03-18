package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Project;
import com.example.fundmatch.domain.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStartupId(Long startupId);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.startup.id = :startupId")
    Long countProjectsByStartupId(@Param("startupId") Long startupId);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.startup.id = :startupId AND p.status = :status")
    Long countProjectsByStatus(@Param("startupId") Long startupId, @Param("status") ProjectStatus status);


}
