package com.example.fundmatch.domain.entities;

import com.example.fundmatch.domain.enums.ProjectStage;
import com.example.fundmatch.domain.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double fundingAmount;
    @Enumerated(EnumType.STRING)
    private ProjectStage stage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private Integer viewCount;

    @ManyToOne
    @JoinColumn(name = "startup_id")
    private Startup startup;
}

