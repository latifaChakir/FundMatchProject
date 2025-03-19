package com.example.fundmatch.domain.entities;

import com.example.fundmatch.domain.enums.MeetingStatus;
import com.example.fundmatch.domain.enums.MeetingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meetingsJoinTable")
public class MeetingJoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String startTime;
    private int duration;
    private String joinUrl;
    private String createdBy;
    @Enumerated(EnumType.STRING)
    private MeetingType type;

    @Enumerated(EnumType.STRING)
    private MeetingStatus status;

}
