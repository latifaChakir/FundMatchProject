package com.example.fundmatch.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "investors")
public class Investor extends User {
    private String organization;

    @ElementCollection
    @CollectionTable(name = "sectors_of_interest", joinColumns = @JoinColumn(name = "investor_id"))
    @Column(name = "sector")
    private List<String> sectorsOfInterest;

    private Double minInvestment;
    private Double maxInvestment;
    private String investmentType;
    private String location;

    @ManyToMany
    @JoinTable(
            name = "investor_saved_projects",
            joinColumns = @JoinColumn(name = "investor_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> savedProjects;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meeting> meetings;
}

