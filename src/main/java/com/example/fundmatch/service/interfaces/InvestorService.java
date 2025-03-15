package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.investor.CreateInvestorRequestDto;
import com.example.fundmatch.domain.vm.InvestorResponseVM;

import java.util.List;

public interface InvestorService {
    InvestorResponseVM saveInvestor(CreateInvestorRequestDto createInvestorRequestDto);

    InvestorResponseVM getInvestorById(Long id);

    InvestorResponseVM updateInvestor(CreateInvestorRequestDto createInvestorRequestDto, Long id);

    void deleteInvestor(Long id);

    List<InvestorResponseVM> getInvestors();

    InvestorResponseVM bookProject(Long projectId);

    InvestorResponseVM getInvestorSavedProjects();

    InvestorResponseVM unsaveProject(Long projectId);
}
