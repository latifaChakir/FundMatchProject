package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Feedback;
import com.example.fundmatch.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProjectIn(List<Project> projects);

    List<Feedback> findByProjectAndIsPrivateFalse(Project project);

    List<Feedback> findByProjectInAndIsPrivateFalse(List<Project> projects);
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.project.startup.id = :startupId")
    long countFeedbacksByStartupId(@Param("startupId") Long startupId);

}
