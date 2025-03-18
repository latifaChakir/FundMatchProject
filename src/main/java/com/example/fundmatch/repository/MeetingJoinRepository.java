package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.MeetingJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingJoinRepository extends JpaRepository<MeetingJoin, Long> {
    List<MeetingJoin> findAllByCreatedBy(String createdBy);
    long countAllByCreatedBy(String createdBy);

    @Query("SELECT COUNT(m) FROM MeetingJoin m WHERE m.createdBy = :createdBy AND m.startTime < :currentTimestamp")
    long countOverdueMeetings(@Param("createdBy") String createdBy, @Param("currentTimestamp") String currentTimestamp);

    // Count upcoming meetings where startTime is after the current time
    @Query("SELECT COUNT(m) FROM MeetingJoin m WHERE m.createdBy = :createdBy AND m.startTime >= :currentTimestamp")
    long countUpcomingMeetings(@Param("createdBy") String createdBy, @Param("currentTimestamp") String currentTimestamp);
}
