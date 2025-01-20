package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.sector.SectorRequest;
import com.example.fundmatch.domain.entities.Sector;
import com.example.fundmatch.domain.mappers.SectorMapper;
import com.example.fundmatch.domain.vm.SectorResponseVM;
import com.example.fundmatch.repository.SectorRepository;
import com.example.fundmatch.service.interfaces.SectorService;
import com.example.fundmatch.shared.exception.SectorNameAlreadyExistsException;
import com.example.fundmatch.shared.exception.SectorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {
    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;
    @Override
    public SectorResponseVM saveSector(SectorRequest sectorRequest) {
        if (sectorRepository.existsByName(sectorRequest.getName())) {
            throw new SectorNameAlreadyExistsException("Sector name already exists.");
        }
        Sector sector = sectorMapper.toEntity(sectorRequest);
        Sector savedSecor = sectorRepository.save(sector);
        return sectorMapper.toDto(savedSecor);
    }

    @Override
    public SectorResponseVM getSectorById(Long id) {
        Optional<Sector> sector = sectorRepository.findById(id);
        if (sector.isEmpty()) {
            throw new SectorNotFoundException("Sector not found.");
        }
        return sectorMapper.toDto(sector.get());
    }

    @Override
    public SectorResponseVM updateSector(SectorRequest sectorRequest, Long id) {
        Optional<Sector> optionalSector = sectorRepository.findById(id);
        if (optionalSector.isEmpty()) {
            throw new SectorNotFoundException("Sector not found.");
        }

        Sector existingSector = optionalSector.get();
        if (!existingSector.getName().equals(sectorRequest.getName()) &&
                sectorRepository.existsByName(sectorRequest.getName())) {
            throw new SectorNameAlreadyExistsException("Secor name already exists.");
        }

        existingSector.setName(sectorRequest.getName());
        Sector updatedSector = sectorRepository.save(existingSector);
        return sectorMapper.toDto(updatedSector);
    }

    @Override
    public void deleteSector(Long id) {
        Optional<Sector> sector = sectorRepository.findById(id);
        if (sector.isEmpty()) {
            throw new SectorNotFoundException("Sector not found.");
        }
        sectorRepository.delete(sector.get());
    }

    @Override
    public List<SectorResponseVM> getSectors() {
        List<Sector> sectors = sectorRepository.findAll();
        return sectorMapper.toDtoList(sectors);
    }
}
