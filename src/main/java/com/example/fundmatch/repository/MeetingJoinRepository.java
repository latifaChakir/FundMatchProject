package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.MeetingJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingJoinRepository extends JpaRepository<MeetingJoin, Long> {
    List<MeetingJoin> findAllByCreatedBy(String createdBy);
}
