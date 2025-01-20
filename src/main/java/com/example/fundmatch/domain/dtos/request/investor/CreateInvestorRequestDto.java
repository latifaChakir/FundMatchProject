package com.example.fundmatch.domain.dtos.request.investor;

import com.example.fundmatch.domain.entities.Sector;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateInvestorRequestDto {
    @NotBlank(message = "Organization name is required")
    private String organization;

    @NotNull(message = "Sectors of interest are required")
    private List<Sector> sectorsOfInterest;

    @NotNull(message = "Minimum investment is required")
    @Positive(message = "Minimum investment must be greater than 0")
    private Double minInvestment;

    @NotNull(message = "Maximum investment is required")
    @Positive(message = "Maximum investment must be greater than 0")
    private Double maxInvestment;

    @NotBlank(message = "Investment type is required")
    private String investmentType;
    @NotNull(message = "location is required")
    private String location;
    @NotNull(message = "experience year is required")
    private Integer experienceYears;

    private Integer averageInvestmentsPerYear;

    private String investmentStrategy;

    private List<String> preferredGeographies;
    private String contactInfo;
}
