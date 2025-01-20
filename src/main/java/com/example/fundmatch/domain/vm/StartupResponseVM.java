package com.example.fundmatch.domain.vm;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StartupResponseVM {
    private Long id;
    private String companyName;
    private String sector;
    private String description;
    private String pitchVideoUrl;
    private Double fundingNeeded;
    private String stage;
    private Integer foundedYear;
    private Integer teamSize;
    private Double revenue;
    private Double growthRate;
    private String headquarters;
    private String contactInfo;
    private List<String> sectors;
    private List<String> stages;
}
