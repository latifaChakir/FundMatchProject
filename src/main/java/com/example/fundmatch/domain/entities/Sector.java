package com.example.fundmatch.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sectors")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

//    @ManyToMany(mappedBy = "sectors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Startup> startups;
//
//    @ManyToMany(mappedBy = "sectors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Investor> investors;
}
