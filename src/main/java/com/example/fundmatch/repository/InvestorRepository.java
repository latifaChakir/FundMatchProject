package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    boolean existsByOrganization(String organization);

    Optional<Investor> findByUserId(Long userId);
}
