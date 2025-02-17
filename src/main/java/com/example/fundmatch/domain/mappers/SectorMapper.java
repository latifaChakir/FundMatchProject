package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.sector.SectorRequest;
import com.example.fundmatch.domain.entities.Sector;
import com.example.fundmatch.domain.vm.SectorResponseVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectorMapper {
    Sector toEntity(SectorRequest request);

    SectorResponseVM toDto(Sector sector);

    List<SectorResponseVM> toDtoList(List<Sector> sectors);
}
