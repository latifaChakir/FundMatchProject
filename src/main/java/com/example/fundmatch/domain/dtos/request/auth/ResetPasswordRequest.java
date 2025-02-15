package com.example.fundmatch.domain.dtos.request.auth;

import lombok.Builder;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
