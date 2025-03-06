package com.example.fundmatch.domain.entities;

import com.example.fundmatch.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDate date;

    private String location;
    private Double cost;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private Integer maxParticipants;
    private Integer availableSpots;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = true)
    private Sector sector;
    private String imagePath;

    @PrePersist
    public void initAvailableSpots() {
        this.availableSpots = this.maxParticipants;
    }
}

