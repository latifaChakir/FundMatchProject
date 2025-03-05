package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.startup.CreateStartupRequestDto;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StartupService  {
    StartupResponseVM saveStartup(CreateStartupRequestDto createStartupRequestDto, MultipartFile file) throws IOException;

    StartupResponseVM getStartupById(Long id);
    StartupResponseVM updateStartup(CreateStartupRequestDto createStartupRequestDto, Long id);
    void deleteStartup(Long id);
    List<StartupResponseVM> getStartups();
}
