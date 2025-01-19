package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.sector.SectorRequest;
import com.example.fundmatch.domain.vm.SectorResponseVM;
import com.example.fundmatch.service.interfaces.SectorService;

import java.util.List;

public class SectorServiceImpl implements SectorService {
    @Override
    public SectorResponseVM saveSector(SectorRequest sectorRequest) {
        return null;
    }

    @Override
    public SectorResponseVM getSectorById(Long id) {
        return null;
    }

    @Override
    public SectorResponseVM updateSector(SectorRequest sectorRequest, Long id) {
        return null;
    }

    @Override
    public void deleteSector(Long id) {

    }

    @Override
    public List<SectorResponseVM> getSectors() {
        return List.of();
    }
}
