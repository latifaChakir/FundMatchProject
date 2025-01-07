package com.example.fundmatch.domain.vm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class TokenResponseVM {
    private String accessToken;
    private String refreshToken;
    private String role;
    private String firstname;
    private String lastname;
}
