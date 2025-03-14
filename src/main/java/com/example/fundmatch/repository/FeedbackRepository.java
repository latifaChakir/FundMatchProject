package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Feedback;
import com.example.fundmatch.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProjectIn(List<Project> projects);

    List<Feedback> findByProjectAndIsPrivateFalse(Project project);

    List<Feedback> findByProjectInAndIsPrivateFalse(List<Project> projects);
}
