package com.example.fundmatch.domain.dtos.request.startup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateStartupRequestDto {
    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Sector is required")
    private String sector;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private String pitchVideoUrl;

    @NotNull(message = "Funding needed is required")
    private Double fundingNeeded;

    @NotBlank(message = "Startup stage is required")
    private String stage;

    @NotNull(message = "Founded year is required")
    private Integer foundedYear;

    @NotNull(message = "Team size is required")
    private Integer teamSize;

    @NotNull(message = "Revenue is required")
    private Double revenue;

    @NotNull(message = "Growth rate is required")
    private Double growthRate;

    @NotBlank(message = "Headquarters is required")
    private String headquarters;

    @NotBlank(message = "Contact info is required")
    private String contactInfo;

    private List<String> sectors;
    private List<String> stages;
}
