package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.startup.CreateStartupRequestDto;
import com.example.fundmatch.domain.entities.Startup;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StartupMapper {
    Startup toEntity(CreateStartupRequestDto dto);
    @Mapping(source = "stages", target = "stages")
    @Mapping(source = "sectors", target = "sectors")
    StartupResponseVM toDto(Startup entity);

    List<StartupResponseVM> toDtoList(List<Startup> startups);
}
