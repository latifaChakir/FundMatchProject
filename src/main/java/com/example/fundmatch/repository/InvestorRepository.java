package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    // These methods need to be native queries to directly access the tables
    @Modifying
    @Query(value = "DELETE FROM investor_sector WHERE investor_id = :investorId", nativeQuery = true)
    void deleteInvestorSectors(@Param("investorId") Long investorId);

    @Modifying
    @Query(value = "DELETE FROM preferred_geographies WHERE investor_id = :investorId", nativeQuery = true)
    void deleteInvestorPreferredGeographies(@Param("investorId") Long investorId);

    @Modifying
    @Query(value = "DELETE FROM investor_saved_projects WHERE investor_id = :investorId", nativeQuery = true)
    void deleteInvestorSavedProjects(@Param("investorId") Long investorId);

    @Modifying
    @Query(value = "DELETE FROM feedbacks WHERE investor_id = :investorId", nativeQuery = true)
    void deleteInvestorFeedbacks(@Param("investorId") Long investorId);

    @Modifying
    @Query(value = "DELETE FROM meetings WHERE investor_id = :investorId", nativeQuery = true)
    void deleteInvestorMeetings(@Param("investorId") Long investorId);
    @Modifying
    @Query(value = "DELETE FROM investors WHERE id = :investorId", nativeQuery = true)
    void deleteInvestorById(@Param("investorId") Long investorId);
}