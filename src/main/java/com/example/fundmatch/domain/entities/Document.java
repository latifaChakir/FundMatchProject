package com.example.fundmatch.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedAt;

    private Boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
