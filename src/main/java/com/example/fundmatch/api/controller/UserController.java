package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.vm.AuthResponseVM;
import com.example.fundmatch.domain.vm.UserResponseVM;
import com.example.fundmatch.service.interfaces.AuthService;
import com.example.fundmatch.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserController{
    private final UserService userService;
    private final AuthService authService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponseVM>>> getAllUsers() {
        List<UserResponseVM> response = userService.getUsers();
        ApiResponse<List<UserResponseVM>> apiResponse = ApiResponse.success(response, "/api/users");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/me")
    public AuthResponseVM me(){
        return authService.getAuthenticatedUser();
    }
}
