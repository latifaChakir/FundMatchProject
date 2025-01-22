package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByEventId(Long eventId);
    List<Reservation> findByUserId(Long userId);
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.event.id = :eventId")
    long countByEventId(@Param("eventId") Long eventId);
}
