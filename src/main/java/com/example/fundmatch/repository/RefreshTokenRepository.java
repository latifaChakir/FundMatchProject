package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.RefreshToken;
import com.example.fundmatch.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Long> {
    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByToken(String token);
}