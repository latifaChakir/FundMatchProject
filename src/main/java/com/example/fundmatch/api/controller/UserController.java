package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.user.UserRequest;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.vm.AuthResponseVM;
import com.example.fundmatch.domain.vm.UserResponseVM;
import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.service.interfaces.AuthService;
import com.example.fundmatch.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/block/{id}")
    public UserResponseVM blockUser(@PathVariable Long id) {
        return userService.blockUser(id);
    }
    @GetMapping("/unBlock/{id}")
    public UserResponseVM unBlockUser(@PathVariable Long id) {
        return userService.unBlockUser(id);
    }
    @PutMapping("/updateMyInfo")
    public ResponseEntity<UserResponseVM> updateUser(
            @Valid @RequestBody UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails");
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        Long userId = userDetails.getUserId();

        UserResponseVM updatedUser = userService.updateUser(userId, userRequest);
        return ResponseEntity.ok(updatedUser);
    }
}
