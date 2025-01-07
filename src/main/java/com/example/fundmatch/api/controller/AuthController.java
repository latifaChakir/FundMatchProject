package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.auth.LoginRequest;
import com.example.fundmatch.domain.dtos.request.auth.RegisterRequest;
import com.example.fundmatch.domain.vm.AuthResponseVM;
import com.example.fundmatch.service.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseVM>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponseVM response = authService.register(registerRequest);
        ApiResponse<AuthResponseVM> apiResponse = ApiResponse.success(response, "/api/auth/register");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseVM>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponseVM response = authService.login(loginRequest);
        ApiResponse<AuthResponseVM> apiResponse = ApiResponse.success(response, "/api/auth/login");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
