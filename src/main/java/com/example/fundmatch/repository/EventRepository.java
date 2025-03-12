package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.isPublished = true")
    List<Event> findPublishedEvents();}
