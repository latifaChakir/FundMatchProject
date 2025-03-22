package com.example.fundmatch.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "investors")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String organization;
    private Integer experienceYears;
    private Integer averageInvestmentsPerYear;

    @ManyToMany
    @JoinTable(
            name = "investor_sector",
            joinColumns = @JoinColumn(name = "investor_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    private List<Sector> sectors;

    @ElementCollection
    @CollectionTable(name = "preferred_geographies", joinColumns = @JoinColumn(name = "investor_id"))
    @Column(name = "geography")
    private List<String> preferredGeographies;

    private Double minInvestment;
    private Double maxInvestment;
    private String investmentType;
    private String location;
    private String investmentStrategy;
    private String contactInfo;

    @ManyToMany
    @JoinTable(
            name = "investor_saved_projects",
            joinColumns = @JoinColumn(name = "investor_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> savedProjects;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.LAZY,  orphanRemoval = true)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.LAZY,  orphanRemoval = true)
    private List<Meeting> meetings;
}
