package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.startup.CreateStartupRequestDto;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import com.example.fundmatch.service.interfaces.StartupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/startups")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class StartupController {
    private  final StartupService startupService;
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<StartupResponseVM>> saveStartup(@Valid @RequestBody CreateStartupRequestDto startupRequest) {
        StartupResponseVM response = startupService.saveStartup(startupRequest);
        ApiResponse<StartupResponseVM> apiResponse = ApiResponse.success(response, "/api/startups/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StartupResponseVM>> updateStartup(@PathVariable long id , @Valid @RequestBody CreateStartupRequestDto startupRequest) {
        StartupResponseVM response = startupService.updateStartup(startupRequest, id);
        ApiResponse<StartupResponseVM> apiResponse = ApiResponse.success(response, "/api/startups");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StartupResponseVM>> getStartup(@PathVariable long id) {
        StartupResponseVM response = startupService.getStartupById(id);
        ApiResponse<StartupResponseVM> apiResponse = ApiResponse.success(response, "/api/startups");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStartup(@PathVariable long id) {
        startupService.deleteStartup(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "/api/startups");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<StartupResponseVM>>> getAllStartups() {
        List<StartupResponseVM> response = startupService.getStartups();
        ApiResponse<List<StartupResponseVM>> apiResponse = ApiResponse.success(response, "/api/startups");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
