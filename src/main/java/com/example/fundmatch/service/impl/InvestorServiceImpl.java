package com.example.fundmatch.service.impl;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.security.CustomUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import com.example.fundmatch.domain.dtos.request.investor.CreateInvestorRequestDto;
import com.example.fundmatch.domain.entities.Investor;
import com.example.fundmatch.domain.mappers.InvestorMapper;
import com.example.fundmatch.domain.vm.InvestorResponseVM;
import com.example.fundmatch.repository.InvestorRepository;
import com.example.fundmatch.service.interfaces.InvestorService;
import com.example.fundmatch.shared.exception.InvestorNotFoundException;
import com.example.fundmatch.shared.exception.InvestorOrganizationAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestorServiceImpl implements InvestorService {
    private final InvestorRepository investorRepository;
    private final InvestorMapper investorMapper;
    private final UserRepository userRepository;

    @Override
    public InvestorResponseVM saveInvestor(CreateInvestorRequestDto createInvestorRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails");
        }

        CustomUserDetails userDetails = (CustomUserDetails) principal;
        Long userId = userDetails.getUserId();
        if (investorRepository.existsByOrganization(createInvestorRequestDto.getOrganization())) {
            throw new InvestorOrganizationAlreadyExistsException("Organization name already exists.");
        }
        Investor investor = investorMapper.toEntity(createInvestorRequestDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        investor.setUser(user);
        Investor savedInvestor = investorRepository.save(investor);
        return investorMapper.toDto(savedInvestor);
    }

    @Override
    public InvestorResponseVM getInvestorById(Long id) {
        Optional<Investor> investor = investorRepository.findById(id);
        if (investor.isEmpty()) {
            throw new InvestorNotFoundException("Investor not found.");
        }
        return investorMapper.toDto(investor.get());
    }

    @Override
    public InvestorResponseVM updateInvestor(CreateInvestorRequestDto createInvestorRequestDto, Long id) {
        Optional<Investor> optionalInvestor = investorRepository.findById(id);
        if (optionalInvestor.isEmpty()) {
            throw new InvestorNotFoundException("Investor not found.");
        }

        Investor existingInvestor = optionalInvestor.get();

        if (!existingInvestor.getOrganization().equals(createInvestorRequestDto.getOrganization()) &&
                investorRepository.existsByOrganization(createInvestorRequestDto.getOrganization())) {
            throw new InvestorOrganizationAlreadyExistsException("Organization name already exists.");
        }

        existingInvestor.setOrganization(createInvestorRequestDto.getOrganization());
        existingInvestor.setSectors(createInvestorRequestDto.getSectorsOfInterest());
        existingInvestor.setMinInvestment(createInvestorRequestDto.getMinInvestment());
        existingInvestor.setMaxInvestment(createInvestorRequestDto.getMaxInvestment());
        existingInvestor.setInvestmentType(createInvestorRequestDto.getInvestmentType());
        existingInvestor.setLocation(createInvestorRequestDto.getLocation());
        existingInvestor.setExperienceYears(createInvestorRequestDto.getExperienceYears());
        existingInvestor.setAverageInvestmentsPerYear(createInvestorRequestDto.getAverageInvestmentsPerYear());
        existingInvestor.setInvestmentStrategy(createInvestorRequestDto.getInvestmentStrategy());
        existingInvestor.setPreferredGeographies(createInvestorRequestDto.getPreferredGeographies());
        existingInvestor.setContactInfo(createInvestorRequestDto.getContactInfo());

        Investor updatedInvestor = investorRepository.save(existingInvestor);
        return investorMapper.toDto(updatedInvestor);
    }

    @Override
    public void deleteInvestor(Long id) {
        Optional<Investor> investor = investorRepository.findById(id);
        if (investor.isEmpty()) {
            throw new InvestorNotFoundException("Investor not found.");
        }
        investorRepository.delete(investor.get());
    }

    @Override
    public List<InvestorResponseVM> getInvestors() {
        List<Investor> investors = investorRepository.findAll();
        return investorMapper.toDtoList(investors);
    }
}
