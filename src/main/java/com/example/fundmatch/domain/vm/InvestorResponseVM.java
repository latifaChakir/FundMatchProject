package com.example.fundmatch.domain.vm;

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
    private List<String> sectorsOfInterest;
    private Double minInvestment;
    private Double maxInvestment;
    private String investmentType;
    private String location;
    private Integer experienceYears;
    private Integer averageInvestmentsPerYear;
    private String investmentStrategy;
    private List<String> preferredGeographies;
    private String contactInfo;
}
