package com.example.fundmatch.service.impl;
import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.shared.exception.InvalidToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import com.example.fundmatch.domain.dtos.request.auth.LoginRequest;
import com.example.fundmatch.domain.dtos.request.auth.RegisterRequest;
import com.example.fundmatch.domain.entities.Role;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.mappers.UserMapper;
import com.example.fundmatch.domain.vm.AuthResponseVM;
import com.example.fundmatch.domain.vm.TokenResponseVM;
import com.example.fundmatch.repository.RoleRepository;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.security.JwtService;
import com.example.fundmatch.service.interfaces.AuthService;
import com.example.fundmatch.service.interfaces.RefreshTokenService;
import com.example.fundmatch.shared.exception.CustomException;
import com.example.fundmatch.shared.exception.EmailAlreadyInUseException;
import com.example.fundmatch.shared.exception.InvalidCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;

    @Override
    public AuthResponseVM register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .isActive(true)
                .createdAt(new Date())
                .isVerified(false)
                .roles(new HashSet<>())
                .build();
        Set<Role> roles = registerRequest.getRoles().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleId)))
                .collect(Collectors.toSet());

        user.getRoles().addAll(roles);

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser, savedUser.getId());
        AuthResponseVM authResponseVM = userMapper.toDto(savedUser);
        authResponseVM.setToken(token);
        return authResponseVM;
    }

    @Override
    public AuthResponseVM login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        String token = jwtService.generateToken(user, user.getId());
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();
        AuthResponseVM authResponseVM = userMapper.toDto(user);
        authResponseVM.setToken(token);
        authResponseVM.setRefreshToken(refreshToken);
        return authResponseVM;
    }
    @Override
    public TokenResponseVM refreshAccessToken(String refreshToken) {
        var token = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new CustomException("Invalid refresh token"));

        String newAccessToken = jwtService.generateToken(token.getUser(), token.getUser().getId());
        String role = token.getUser().getRoles().toString();
        String firstname = token.getUser().getFirstName();
        String lastname = token.getUser().getLastName();

        return new TokenResponseVM(newAccessToken, refreshToken , role , firstname , lastname);
    }
    @Override
    public AuthResponseVM getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails");
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        Long userId = userDetails.getUserId();
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        return userMapper.toDto(user);
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        System.out.println("User: " + user);
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000));

        userRepository.save(user);
        String resetLink = "http://localhost:4200/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(user.getEmail(), resetLink);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new InvalidToken("Invalid or expired token"));

        if (user.getResetTokenExpiration().before(new Date())) {
            throw new IllegalArgumentException("Token expired");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);
        userRepository.save(user);
    }


}
