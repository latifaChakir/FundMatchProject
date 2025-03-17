package com.example.fundmatch;

import com.example.fundmatch.domain.dtos.request.investor.CreateInvestorRequestDto;
import com.example.fundmatch.domain.entities.Investor;
import com.example.fundmatch.domain.entities.Project;
import com.example.fundmatch.domain.entities.Sector;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.mappers.InvestorMapper;
import com.example.fundmatch.domain.vm.InvestorResponseVM;
import com.example.fundmatch.repository.InvestorRepository;
import com.example.fundmatch.repository.ProjectRepository;
import com.example.fundmatch.repository.SectorRepository;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.service.impl.InvestorServiceImpl;
import com.example.fundmatch.shared.exception.InvestorNotFoundException;
import com.example.fundmatch.shared.exception.InvestorOrganizationAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvestorServiceTest {
    @InjectMocks
    private InvestorServiceImpl investorService;

    @Mock
    private InvestorRepository investorRepository;

    @Mock
    private InvestorMapper investorMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails customUserDetails;

    @Mock
    private CreateInvestorRequestDto createInvestorRequestDto;

    @Mock
    private User user;

    @Mock
    private Investor investor;

    @Mock
    private Sector sector;

    @Mock
    private Project project;

    @Mock
    private InvestorResponseVM investorResponseVM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void saveInvestor_WhenOrganizationExists_ThrowsInvestorOrganizationAlreadyExistsException() {
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(customUserDetails.getUserId()).thenReturn(1L);
        when(investorRepository.existsByOrganization("Existing Organization")).thenReturn(true);
        when(createInvestorRequestDto.getOrganization()).thenReturn("Existing Organization");

        assertThrows(InvestorOrganizationAlreadyExistsException.class, () -> investorService.saveInvestor(createInvestorRequestDto));
    }

    @Test
    void saveInvestor_WhenUserNotFound_ThrowsIllegalArgumentException() {
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(customUserDetails.getUserId()).thenReturn(1L);
        when(investorRepository.existsByOrganization(anyString())).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> investorService.saveInvestor(createInvestorRequestDto));
    }

    @Test
    void saveInvestor_WhenSuccessful_ReturnsInvestorResponse() {
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(customUserDetails.getUserId()).thenReturn(1L);
        when(investorRepository.existsByOrganization(anyString())).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(investorMapper.toEntity(createInvestorRequestDto)).thenReturn(investor);
        when(investorRepository.save(investor)).thenReturn(investor);
        when(investorMapper.toDto(investor)).thenReturn(investorResponseVM);

        List<Sector> sectors = new ArrayList<>();
        sectors.add(sector);
        when(createInvestorRequestDto.getSectors()).thenReturn(sectors);
        when(sectorRepository.findById(sector.getId())).thenReturn(Optional.of(sector));

        InvestorResponseVM result = investorService.saveInvestor(createInvestorRequestDto);

        assertEquals(investorResponseVM, result);
        verify(investorRepository).save(investor);
    }

    @Test
    void getInvestorById_WhenInvestorExists_ReturnsInvestor() {
        Long investorId = 1L;
        when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));
        when(investorMapper.toDto(investor)).thenReturn(investorResponseVM);

        InvestorResponseVM result = investorService.getInvestorById(investorId);

        assertEquals(investorResponseVM, result);
    }

    @Test
    void getInvestorById_WhenInvestorDoesNotExist_ThrowsInvestorNotFoundException() {
        Long investorId = 1L;
        when(investorRepository.findById(investorId)).thenReturn(Optional.empty());

        assertThrows(InvestorNotFoundException.class, () -> investorService.getInvestorById(investorId));
    }

    @Test
    void updateInvestor_WhenInvestorExists_UpdatesInvestor() {
        Long investorId = 1L;
        when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));
        when(investorMapper.toEntity(createInvestorRequestDto)).thenReturn(investor);
        when(investorRepository.save(investor)).thenReturn(investor);
        when(investorMapper.toDto(investor)).thenReturn(investorResponseVM);
        when(investor.getOrganization()).thenReturn("Old Organization");
        when(createInvestorRequestDto.getOrganization()).thenReturn("New Organization");
        when(investorRepository.existsByOrganization("New Organization")).thenReturn(false);
        InvestorResponseVM result = investorService.updateInvestor(createInvestorRequestDto, investorId);
        assertEquals(investorResponseVM, result);
        verify(investorRepository).save(investor);
    }

    @Test
    void updateInvestor_WhenInvestorDoesNotExist_ThrowsInvestorNotFoundException() {
        Long investorId = 1L;
        when(investorRepository.findById(investorId)).thenReturn(Optional.empty());
        assertThrows(InvestorNotFoundException.class, () -> investorService.updateInvestor(createInvestorRequestDto, investorId));
    }

    @Test
    void deleteInvestor_WhenInvestorExists_DeletesInvestor() {
        Long investorId = 1L;
        when(investorRepository.findById(investorId)).thenReturn(Optional.of(investor));
        investorService.deleteInvestor(investorId);
        verify(investorRepository).delete(investor);
    }

    @Test
    void deleteInvestor_WhenInvestorDoesNotExist_ThrowsInvestorNotFoundException() {
        Long investorId = 1L;
        when(investorRepository.findById(investorId)).thenReturn(Optional.empty());
        assertThrows(InvestorNotFoundException.class, () -> investorService.deleteInvestor(investorId));
    }

    @Test
    void getInvestors_ReturnsAllInvestors() {
        List<Investor> investors = Arrays.asList(investor);
        when(investorRepository.findAll()).thenReturn(investors);
        when(investorMapper.toDtoList(investors)).thenReturn(Arrays.asList(investorResponseVM));
        List<InvestorResponseVM> result = investorService.getInvestors();
        assertEquals(1, result.size());
        assertEquals(investorResponseVM, result.get(0));
    }

    @Test
    void getInvestorByUserId_WhenInvestorExists_ReturnsInvestor() {
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(customUserDetails.getUserId()).thenReturn(1L);
        when(investorRepository.findByUserId(1L)).thenReturn(Optional.of(investor));
        when(investorMapper.toDto(investor)).thenReturn(investorResponseVM);
        InvestorResponseVM result = investorService.getInvestorByUserId();

        assertEquals(investorResponseVM, result);
    }

    @Test
    void getInvestorByUserId_WhenInvestorDoesNotExist_ThrowsEntityNotFoundException() {
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(customUserDetails.getUserId()).thenReturn(1L);
        when(investorRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> investorService.getInvestorByUserId());
    }
}
