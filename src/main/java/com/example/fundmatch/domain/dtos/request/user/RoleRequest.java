package com.example.fundmatch.domain.dtos.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleRequest {
    @NotBlank(message = "role name is required")
    private String name;
}
