package com.example.fundmatch.domain.vm;

import com.example.fundmatch.domain.entities.Project;
import com.example.fundmatch.domain.entities.Sector;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InvestorResponseVM {
    private Long id;
    private String organization;
    private List<SectorResponseVM> sectors;
    private Double minInvestment;
    private Double maxInvestment;
    private String investmentType;
    private String location;
    private UserResponseVM user;
    private Integer experienceYears;
    private Integer averageInvestmentsPerYear;
    private String investmentStrategy;
    private List<String> preferredGeographies;
    private String contactInfo;
    private List<ProjectResponseVM> savedProjects;

}
