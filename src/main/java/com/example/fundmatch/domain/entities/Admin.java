package com.example.fundmatch.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User {
    private String adminLevel;
}