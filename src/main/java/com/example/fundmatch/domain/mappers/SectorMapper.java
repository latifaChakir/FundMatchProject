package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.sector.SectorRequest;
import com.example.fundmatch.domain.entities.Sector;
import com.example.fundmatch.domain.vm.SectorResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface SectorMapper {
    Sector toEntity(SectorRequest stageRequest);
    SectorResponseVM toDto(Sector stage);
}
