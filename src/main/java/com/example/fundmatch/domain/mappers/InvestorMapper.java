package com.example.fundmatch.domain.mappers;


import com.example.fundmatch.domain.dtos.request.investor.CreateInvestorRequestDto;
import com.example.fundmatch.domain.entities.Investor;
import com.example.fundmatch.domain.vm.InvestorResponseVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvestorMapper {
    Investor toEntity(CreateInvestorRequestDto dto);
    InvestorResponseVM toDto(Investor entity);

    List<InvestorResponseVM> toDtoList(List<Investor> investors);
}

