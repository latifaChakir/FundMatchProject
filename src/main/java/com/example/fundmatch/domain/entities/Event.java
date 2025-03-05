package com.example.fundmatch.domain.entities;

import com.example.fundmatch.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
}

