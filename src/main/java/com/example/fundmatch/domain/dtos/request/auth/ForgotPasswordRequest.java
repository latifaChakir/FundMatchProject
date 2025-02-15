package com.example.fundmatch.domain.dtos.request.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ForgotPasswordRequest {
    private String email;
}
