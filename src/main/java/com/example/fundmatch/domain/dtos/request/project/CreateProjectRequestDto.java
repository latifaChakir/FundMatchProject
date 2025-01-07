package com.example.fundmatch.domain.dtos.request.project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateProjectRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Funding amount is required")
    private Double fundingAmount;

    @NotBlank(message = "Project stage is required")
    private String stage;
}

