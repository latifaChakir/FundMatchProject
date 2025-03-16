package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.startup.CreateStartupRequestDto;
import com.example.fundmatch.domain.entities.*;
import com.example.fundmatch.domain.mappers.StartupMapper;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import com.example.fundmatch.repository.SectorRepository;
import com.example.fundmatch.repository.StageRepository;
import com.example.fundmatch.repository.StartupRepository;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.service.interfaces.StartupService;
import com.example.fundmatch.shared.exception.DuplicateResourceException;
import com.example.fundmatch.shared.exception.ResourceNotFoundException;
import com.example.fundmatch.shared.exception.SectorNotFoundException;
import com.example.fundmatch.shared.exception.StageNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StartupServiceImpl implements StartupService {
    private final StartupRepository startupRepository;
    private final StartupMapper startupMapper;
    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;
    private final StageRepository stageRepository;
    private final FileStorageService fileStorageService;


    @Override
    public StartupResponseVM saveStartup(CreateStartupRequestDto createStartupRequestDto, MultipartFile file) throws IOException {
        if(file==null){
            System.out.println("ok file canot be null");
        }
        String imagePath = (file != null) ? fileStorageService.saveFile(file) : null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails");
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        Long userId = userDetails.getUserId();
        if (startupRepository.existsByCompanyName(createStartupRequestDto.getCompanyName())) {
            throw new DuplicateResourceException("Company name already exists.");
        }
        Startup startup = startupMapper.toEntity(createStartupRequestDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        startup.setUser(user);
        startup.setImagePath(imagePath);
        List<Sector> sectors = createStartupRequestDto.getSectors().stream()
                .map(sector -> sectorRepository.findById(sector.getId())
                        .orElseThrow(() -> new SectorNotFoundException("Sector not found with ID: " + sector.getId())))
                .toList();
        startup.setSectors(sectors);
        List<Stage> stages = createStartupRequestDto.getStages().stream()
                .map(stage -> stageRepository.findById(stage.getId())
                        .orElseThrow(() -> new StageNotFoundException("Stage not found with ID: " + stage.getId())))
                .toList();
        startup.setSectors(sectors);
        System.out.println(startup);
        Startup savedStartup = startupRepository.save(startup);
        return startupMapper.toDto(savedStartup);
    }

    @Override
    public StartupResponseVM getStartupById(Long id) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Startup not found."));
        return startupMapper.toDto(startup);
    }

    @Override
    public StartupResponseVM updateStartup(CreateStartupRequestDto createStartupRequestDto, Long id, MultipartFile file) throws IOException {
        System.out.println("hello from update");
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Startup not found."));

        if (!startup.getCompanyName().equals(createStartupRequestDto.getCompanyName()) &&
                startupRepository.existsByCompanyName(createStartupRequestDto.getCompanyName())) {
            throw new DuplicateResourceException("Company name already exists.");
        }

        if (file != null && !file.isEmpty()) {
            String imagePath = fileStorageService.saveFile(file);
            startup.setImagePath(imagePath);
        }

        startup.setCompanyName(createStartupRequestDto.getCompanyName());
        startup.setDescription(createStartupRequestDto.getDescription());
        startup.setPitchVideoUrl(createStartupRequestDto.getPitchVideoUrl());
        startup.setFundingNeeded(createStartupRequestDto.getFundingNeeded());
        startup.setFoundedYear(createStartupRequestDto.getFoundedYear());
        startup.setTeamSize(createStartupRequestDto.getTeamSize());
        startup.setRevenue(createStartupRequestDto.getRevenue());
        startup.setGrowthRate(createStartupRequestDto.getGrowthRate());
        startup.setHeadquarters(createStartupRequestDto.getHeadquarters());
        startup.setContactInfo(createStartupRequestDto.getContactInfo());

        List<Sector> sectors = createStartupRequestDto.getSectors().stream()
                .map(sector -> sectorRepository.findById(sector.getId())
                        .orElseThrow(() -> new SectorNotFoundException("Sector not found with ID: " + sector.getId())))
                .collect(Collectors.toList());
        startup.setSectors(sectors);

        List<Stage> stages = createStartupRequestDto.getStages().stream()
                .map(stage -> stageRepository.findById(stage.getId())
                        .orElseThrow(() -> new StageNotFoundException("Stage not found with ID: " + stage.getId())))
                .collect(Collectors.toList());
        startup.setStages(stages);

        Startup updatedStartup = startupRepository.save(startup);
        return startupMapper.toDto(updatedStartup);
    }

    @Override
    public void deleteStartup(Long id) {
        if (!startupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Startup not found.");
        }
        startupRepository.deleteById(id);
    }

    @Override
    public List<StartupResponseVM> getStartups() {
        List<Startup> startups = startupRepository.findAll();
        return startupMapper.toDtoList(startups);
    }

    @Override
    public StartupResponseVM getStartupByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails.");
        }

        Long userId = userDetails.getUserId();
        Startup startup = startupRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Startup not found for User ID " + userId + "."));
        return startupMapper.toDto(startup);
    }
}
