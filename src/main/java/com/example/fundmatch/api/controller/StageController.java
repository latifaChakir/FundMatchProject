package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.auth.RegisterRequest;
import com.example.fundmatch.domain.dtos.request.stage.StageRequest;
import com.example.fundmatch.domain.vm.StageResponseVM;
import com.example.fundmatch.service.interfaces.StageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class StageController {
    private final StageService stageService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<StageResponseVM>> saveStage(@Valid @RequestBody StageRequest stageRequest) {
        StageResponseVM response = stageService.saveStage(stageRequest);
        ApiResponse<StageResponseVM> apiResponse = ApiResponse.success(response, "/api/stages/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StageResponseVM>> updateStage(@PathVariable long id , @Valid @RequestBody StageRequest stageRequest) {
        StageResponseVM response = stageService.updateStage(stageRequest, id);
        ApiResponse<StageResponseVM> apiResponse = ApiResponse.success(response, "/api/stages");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StageResponseVM>> getStage(@PathVariable long id) {
        StageResponseVM response = stageService.getStageById(id);
        ApiResponse<StageResponseVM> apiResponse = ApiResponse.success(response, "/api/stages");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStage(@PathVariable long id) {
        stageService.deleteStage(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "/api/stages");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<StageResponseVM>>> getAllStages() {
        List<StageResponseVM> response = stageService.getStages();
        ApiResponse<List<StageResponseVM>> apiResponse = ApiResponse.success(response, "/api/stages");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
