package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    boolean existsByOrganization(String organization);

    Optional<Investor> findByUserId(Long userId);
    @Query("SELECT COUNT(p) FROM Investor i JOIN i.savedProjects p WHERE i.id = :investorId")
    long countSavedProjects(@Param("investorId") Long investorId);

}
