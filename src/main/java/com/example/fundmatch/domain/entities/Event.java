package com.example.fundmatch.domain.entities;

import com.example.fundmatch.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String location;
    private Double cost;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private Integer maxParticipants;
}

