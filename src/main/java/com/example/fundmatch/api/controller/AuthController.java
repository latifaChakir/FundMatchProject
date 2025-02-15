    package com.example.fundmatch.api.controller;

    import com.example.fundmatch.api.wrapper.ApiResponse;
    import com.example.fundmatch.domain.dtos.request.auth.ForgotPasswordRequest;
    import com.example.fundmatch.domain.dtos.request.auth.LoginRequest;
    import com.example.fundmatch.domain.dtos.request.auth.RegisterRequest;
    import com.example.fundmatch.domain.dtos.request.auth.ResetPasswordRequest;
    import com.example.fundmatch.domain.vm.AuthResponseVM;
    import com.example.fundmatch.service.interfaces.AuthService;
    import jakarta.validation.Valid;
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
        @PostMapping("/forgot-password")
        public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
            authService.forgotPassword(request.getEmail());
            ApiResponse<String> apiResponse = ApiResponse.success("Password reset email sent", "/api/auth/forgot-password");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }


        @PutMapping("/reset-password")
        public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request) {
            authService.resetPassword(request.getToken(), request.getNewPassword());
            ApiResponse<String> apiResponse = ApiResponse.success("Password successfully reset", "/api/auth/reset-password");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

    }
