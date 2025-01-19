package com.example.fundmatch.domain.dtos.request.sector;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SectorRequest {
    @NotBlank(message = "Sector name is required")
    @Size(max = 100, message = "Sector name cannot exceed 100 characters")
    private String name;
}
