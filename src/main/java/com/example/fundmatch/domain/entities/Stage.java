package com.example.fundmatch.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stages")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Ex. "Seed", "Series A", "Growth", etc.
//
//    @ManyToMany(mappedBy = "stages", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Startup> startups;
}
