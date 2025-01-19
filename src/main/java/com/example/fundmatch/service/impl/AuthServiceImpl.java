package com.example.fundmatch.service.impl;

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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
                .isActive(registerRequest.isActive())
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
}
