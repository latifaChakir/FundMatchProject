package com.example.fundmatch.domain.dtos.request.investor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private List<String> sectorsOfInterest;

    @NotNull(message = "Minimum investment is required")
    private Double minInvestment;

    @NotNull(message = "Maximum investment is required")
    private Double maxInvestment;

    @NotBlank(message = "Investment type is required")
    private String investmentType;

    private String location;
}

