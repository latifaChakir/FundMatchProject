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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stages")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class StageController {
    private final StageService stageService;
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<StageResponseVM>> register(@Valid @RequestBody StageRequest stageRequest) {
        StageResponseVM response = stageService.saveStage(stageRequest);
        ApiResponse<StageResponseVM> apiResponse = ApiResponse.success(response, "/api/stages/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
