package com.example.fundmatch.domain.dtos.request.startup;

import com.example.fundmatch.domain.entities.Sector;
import com.example.fundmatch.domain.entities.Stage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateStartupRequestDto {
    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private String pitchVideoUrl;

    @NotNull(message = "Funding needed is required")
    private Double fundingNeeded;

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
    private MultipartFile imagePath;

    private List<Sector> sectors;
    private List<Stage> stages;
}
