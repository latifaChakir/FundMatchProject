package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.entities.RefreshToken;
import com.example.fundmatch.domain.entities.User;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
}
