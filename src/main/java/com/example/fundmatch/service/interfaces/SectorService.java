package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.sector.SectorRequest;
import com.example.fundmatch.domain.vm.SectorResponseVM;

import java.util.List;

public interface SectorService {
    SectorResponseVM saveSector(SectorRequest sectorRequest);
    SectorResponseVM getSectorById(Long id);
    SectorResponseVM updateSector(SectorRequest sectorRequest, Long id);
    void deleteSector(Long id);
    List<SectorResponseVM> getSectors();

}
