package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email);
}