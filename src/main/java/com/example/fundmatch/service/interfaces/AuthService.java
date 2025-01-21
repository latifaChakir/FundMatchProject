package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.auth.LoginRequest;
import com.example.fundmatch.domain.dtos.request.auth.RegisterRequest;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.vm.AuthResponseVM;
import com.example.fundmatch.domain.vm.TokenResponseVM;

public interface AuthService {
    AuthResponseVM register(RegisterRequest request);
    AuthResponseVM login(LoginRequest request);
    TokenResponseVM refreshAccessToken(String refreshToken);
    User getAuthenticatedUser();

}
