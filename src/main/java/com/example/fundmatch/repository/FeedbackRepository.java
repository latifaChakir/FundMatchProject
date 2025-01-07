package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
