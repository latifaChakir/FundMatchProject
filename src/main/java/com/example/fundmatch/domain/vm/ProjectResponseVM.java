package com.example.fundmatch.domain.vm;

import com.example.fundmatch.domain.entities.Startup;
import com.example.fundmatch.domain.enums.ProjectStage;
import com.example.fundmatch.domain.enums.ProjectStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectResponseVM {
    private Long id;
    private String title;
    private String description;
    private Double fundingAmount;
    private ProjectStage stage;
    private LocalDate createdAt;
    private ProjectStatus status;
    private Integer viewCount;
    private String imagePath;
    private StartupResponseVM startup;
}
