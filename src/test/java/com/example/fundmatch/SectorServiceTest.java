package com.example.fundmatch;

import com.example.fundmatch.domain.dtos.request.sector.SectorRequest;
import com.example.fundmatch.domain.entities.Sector;
import com.example.fundmatch.domain.mappers.SectorMapper;
import com.example.fundmatch.domain.vm.SectorResponseVM;
import com.example.fundmatch.repository.SectorRepository;
import com.example.fundmatch.service.impl.SectorServiceImpl;
import com.example.fundmatch.shared.exception.SectorNameAlreadyExistsException;
import com.example.fundmatch.shared.exception.SectorNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class SectorServiceTest {
    @InjectMocks
    private SectorServiceImpl sectorService;

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private SectorMapper sectorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveSector_WhenSectorNameExists_ThrowsSectorNameAlreadyExistsException() {
        SectorRequest sectorRequest = new SectorRequest();
        sectorRequest.setName("Finance");
        when(sectorRepository.existsByName("Finance")).thenReturn(true);

        assertThrows(SectorNameAlreadyExistsException.class, () -> sectorService.saveSector(sectorRequest));
        verify(sectorRepository, never()).save(any());
    }

    @Test
    void saveSector_WhenValidRequest_SavesSector() {
        SectorRequest sectorRequest = new SectorRequest();
        sectorRequest.setName("Finance");
        Sector sector = new Sector();
        sector.setName("Finance");
        SectorResponseVM response = new SectorResponseVM();
        response.setName("Finance");

        when(sectorRepository.existsByName("Finance")).thenReturn(false);
        when(sectorMapper.toEntity(sectorRequest)).thenReturn(sector);
        when(sectorRepository.save(sector)).thenReturn(sector);
        when(sectorMapper.toDto(sector)).thenReturn(response);

        SectorResponseVM result = sectorService.saveSector(sectorRequest);

        assertEquals("Finance", result.getName());
        verify(sectorRepository).save(sector);
    }

    @Test
    void getSectorById_WhenSectorExists_ReturnsSector() {
        Long sectorId = 1L;
        Sector sector = new Sector();
        sector.setId(sectorId);
        sector.setName("Finance");
        SectorResponseVM response = new SectorResponseVM();
        response.setName("Finance");

        when(sectorRepository.findById(sectorId)).thenReturn(Optional.of(sector));
        when(sectorMapper.toDto(sector)).thenReturn(response);

        SectorResponseVM result = sectorService.getSectorById(sectorId);

        assertEquals("Finance", result.getName());
    }

    @Test
    void getSectorById_WhenSectorDoesNotExist_ThrowsSectorNotFoundException() {
        Long sectorId = 1L;
        when(sectorRepository.findById(sectorId)).thenReturn(Optional.empty());

        assertThrows(SectorNotFoundException.class, () -> sectorService.getSectorById(sectorId));
    }

    @Test
    void updateSector_WhenSectorExists_UpdatesSector() {
        Long sectorId = 1L;
        SectorRequest sectorRequest = new SectorRequest();
        sectorRequest.setName("Finance");
        Sector existingSector = new Sector();
        existingSector.setId(sectorId);
        existingSector.setName("OldName");
        Sector updatedSector = new Sector();
        updatedSector.setId(sectorId);
        updatedSector.setName("Finance");
        SectorResponseVM response = new SectorResponseVM();
        response.setName("Finance");

        when(sectorRepository.findById(sectorId)).thenReturn(Optional.of(existingSector));
        when(sectorRepository.existsByName("Finance")).thenReturn(false);
        when(sectorMapper.toEntity(sectorRequest)).thenReturn(updatedSector);
        when(sectorRepository.save(existingSector)).thenReturn(updatedSector);
        when(sectorMapper.toDto(updatedSector)).thenReturn(response);

        SectorResponseVM result = sectorService.updateSector(sectorRequest, sectorId);

        assertEquals("Finance", result.getName());
        verify(sectorRepository).save(existingSector);
    }

    @Test
    void updateSector_WhenSectorDoesNotExist_ThrowsSectorNotFoundException() {
        Long sectorId = 1L;
        SectorRequest sectorRequest = new SectorRequest();
        sectorRequest.setName("Finance");
        when(sectorRepository.findById(sectorId)).thenReturn(Optional.empty());

        assertThrows(SectorNotFoundException.class, () -> sectorService.updateSector(sectorRequest, sectorId));
    }

    @Test
    void deleteSector_WhenSectorExists_DeletesSector() {
        Long sectorId = 1L;
        Sector sector = new Sector();
        sector.setId(sectorId);

        when(sectorRepository.findById(sectorId)).thenReturn(Optional.of(sector));

        sectorService.deleteSector(sectorId);

        verify(sectorRepository).delete(sector);
    }

    @Test
    void deleteSector_WhenSectorDoesNotExist_ThrowsSectorNotFoundException() {
        Long sectorId = 1L;
        when(sectorRepository.findById(sectorId)).thenReturn(Optional.empty());

        assertThrows(SectorNotFoundException.class, () -> sectorService.deleteSector(sectorId));
    }

    @Test
    void getSectors_ReturnsAllSectors() {
        Sector sector1 = new Sector();
        sector1.setName("Finance");
        Sector sector2 = new Sector();
        sector2.setName("Health");
        List<Sector> sectors = Arrays.asList(sector1, sector2);
        SectorResponseVM response1 = new SectorResponseVM();
        response1.setName("Finance");
        SectorResponseVM response2 = new SectorResponseVM();
        response2.setName("Health");

        when(sectorRepository.findAll()).thenReturn(sectors);
        when(sectorMapper.toDtoList(sectors)).thenReturn(Arrays.asList(response1, response2));

        List<SectorResponseVM> result = sectorService.getSectors();

        assertEquals(2, result.size());
        assertEquals("Finance", result.get(0).getName());
        assertEquals("Health", result.get(1).getName());
    }
}
