package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.startup.CreateStartupRequestDto;
import com.example.fundmatch.domain.entities.Startup;
import com.example.fundmatch.domain.mappers.StartupMapper;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import com.example.fundmatch.repository.StartupRepository;
import com.example.fundmatch.service.interfaces.StartupService;
import com.example.fundmatch.shared.exception.DuplicateResourceException;
import com.example.fundmatch.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StartupServiceImpl implements StartupService {
    private final StartupRepository startupRepository;
    private final StartupMapper startupMapper;

    @Override
    public StartupResponseVM saveStartup(CreateStartupRequestDto createStartupRequestDto) {
        if (startupRepository.existsByCompanyName(createStartupRequestDto.getCompanyName())) {
            throw new DuplicateResourceException("Company name already exists.");
        }
        Startup startup = startupMapper.toEntity(createStartupRequestDto);
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
    public StartupResponseVM updateStartup(CreateStartupRequestDto createStartupRequestDto, Long id) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Startup not found."));

        if (!startup.getCompanyName().equals(createStartupRequestDto.getCompanyName()) &&
                startupRepository.existsByCompanyName(createStartupRequestDto.getCompanyName())) {
            throw new DuplicateResourceException("Company name already exists.");
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
        startup.setSectors(createStartupRequestDto.getSectors());
        startup.setStages(createStartupRequestDto.getStages());

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
}
