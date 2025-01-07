package com.example.fundmatch.domain.entities;

import com.example.fundmatch.domain.enums.MeetingStatus;
import com.example.fundmatch.domain.enums.MeetingType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "startup_id")
    private Startup startup;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    private Investor investor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @Enumerated(EnumType.STRING)
    private MeetingType type;

    @Enumerated(EnumType.STRING)
    private MeetingStatus status;

    private String notes;
}

