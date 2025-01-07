package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.auth.LoginRequest;
import com.example.fundmatch.domain.dtos.request.auth.RegisterRequest;
import com.example.fundmatch.domain.vm.AuthResponseVM;

public interface AuthService {
    AuthResponseVM register(RegisterRequest request);
    AuthResponseVM login(LoginRequest request);
}
