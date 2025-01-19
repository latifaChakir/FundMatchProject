package com.example.fundmatch.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "startups")
public class Startup extends User {
    @Column(nullable = false)
    private String companyName;

    @ManyToMany
    @JoinTable(
            name = "startup_sector",
            joinColumns = @JoinColumn(name = "startup_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    private List<Sector> sectors;

    private String description;
    private String pitchVideoUrl;
    private Double fundingNeeded;

    @ManyToMany
    @JoinTable(
            name = "startup_stage",
            joinColumns = @JoinColumn(name = "startup_id"),
            inverseJoinColumns = @JoinColumn(name = "stage_id")
    )
    private List<Stage> stages;

    private Integer foundedYear;
    private Integer teamSize;
    private Double revenue;
    private Double growthRate;
    private String headquarters;
    private String contactInfo;

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meeting> meetings;
}
