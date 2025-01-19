package com.example.fundmatch.domain.dtos.request.stage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StageRequest {
    @NotBlank(message = "Stage name is required")
    @Size(max = 100, message = "Stage name cannot exceed 100 characters")
    private String name;
}
