package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.sector.SectorRequest;
import com.example.fundmatch.domain.vm.SectorResponseVM;
import com.example.fundmatch.service.interfaces.SectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectors")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class SectorController {
    private final SectorService sectorService;
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<SectorResponseVM>> saveSector(@Valid @RequestBody SectorRequest sectorRequest) {
        SectorResponseVM response = sectorService.saveSector(sectorRequest);
        ApiResponse<SectorResponseVM> apiResponse = ApiResponse.success(response, "/api/sectors/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SectorResponseVM>> updateSector(@PathVariable long id , @Valid @RequestBody SectorRequest sectorRequest) {
        SectorResponseVM response = sectorService.updateSector(sectorRequest, id);
        ApiResponse<SectorResponseVM> apiResponse = ApiResponse.success(response, "/api/sectors");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SectorResponseVM>> getSector(@PathVariable long id) {
        SectorResponseVM response = sectorService.getSectorById(id);
        ApiResponse<SectorResponseVM> apiResponse = ApiResponse.success(response, "/api/sectors");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSector(@PathVariable long id) {
        sectorService.deleteSector(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "/api/sectors");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SectorResponseVM>>> getAllSectors() {
        List<SectorResponseVM> response = sectorService.getSectors();
        ApiResponse<List<SectorResponseVM>> apiResponse = ApiResponse.success(response, "/api/sectors");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
