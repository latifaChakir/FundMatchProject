package com.example.fundmatch.domain.mappers;


import com.example.fundmatch.domain.dtos.request.investor.CreateInvestorRequestDto;
import com.example.fundmatch.domain.entities.Investor;
import com.example.fundmatch.domain.vm.InvestorResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface InvestorMapper {
    Investor toEntity(CreateInvestorRequestDto dto);
    @Mapping(source = "sectors", target = "sectors")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "savedProjects", target = "savedProjects")
    InvestorResponseVM toDto(Investor entity);

    List<InvestorResponseVM> toDtoList(List<Investor> investors);
}


