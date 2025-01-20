package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.startup.CreateStartupRequestDto;
import com.example.fundmatch.domain.vm.StartupResponseVM;

import java.util.List;

public interface StartupService  {
    StartupResponseVM saveStartup(CreateStartupRequestDto createStartupRequestDto);
    StartupResponseVM getStartupById(Long id);
    StartupResponseVM updateStartup(CreateStartupRequestDto createStartupRequestDto, Long id);
    void deleteStartup(Long id);
    List<StartupResponseVM> getStartups();
}
