package com.example.fundmatch.domain.dtos.request.project;
import com.example.fundmatch.domain.enums.ProjectStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

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
    @PastOrPresent(message = "Created date cannot be in the future")
    private LocalDate createdAt;
    @NotNull(message = "Project stage is required")
    private ProjectStage stage;
    @NotNull(message = "viewCount is required")
    private Integer viewCount;
    private MultipartFile imagePath;
}

