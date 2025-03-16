package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.startup.CreateStartupRequestDto;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import com.example.fundmatch.service.interfaces.StartupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/startups")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class StartupController {
    private  final StartupService startupService;
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StartupResponseVM>> saveStartup(
            @Valid @ModelAttribute CreateStartupRequestDto startupRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        if (file == null) {
            System.out.println("Le fichier est null.");
        }
        StartupResponseVM response = startupService.saveStartup(startupRequest, file);
        ApiResponse<StartupResponseVM> apiResponse = ApiResponse.success(response, "/api/startups/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @PutMapping(value = "/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StartupResponseVM>> updateStartup(
            @PathVariable long id ,
            @Valid @ModelAttribute CreateStartupRequestDto startupRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException
    {
        StartupResponseVM response = startupService.updateStartup(startupRequest, id,file);
        ApiResponse<StartupResponseVM> apiResponse = ApiResponse.success(response, "/api/startups");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/detail/{id}")
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
    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<StartupResponseVM>>> getAllStartups() {
        List<StartupResponseVM> response = startupService.getStartups();
        ApiResponse<List<StartupResponseVM>> apiResponse = ApiResponse.success(response, "/api/startups");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/getStartup")
    public StartupResponseVM getInvestor() {
        return startupService.getStartupByUserId();
    }
}
