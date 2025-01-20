package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.investor.CreateInvestorRequestDto;
import com.example.fundmatch.domain.vm.InvestorResponseVM;
import com.example.fundmatch.service.interfaces.InvestorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investors")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class InvestorController{
    private  final InvestorService investorService;
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<InvestorResponseVM>> saveInvestor(@Valid @RequestBody CreateInvestorRequestDto investorRequest) {
        InvestorResponseVM response = investorService.saveInvestor(investorRequest);
        ApiResponse<InvestorResponseVM> apiResponse = ApiResponse.success(response, "/api/investors/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InvestorResponseVM>> updateInvestor(@PathVariable long id , @Valid @RequestBody CreateInvestorRequestDto investorRequest) {
        InvestorResponseVM response = investorService.updateInvestor(investorRequest, id);
        ApiResponse<InvestorResponseVM> apiResponse = ApiResponse.success(response, "/api/investors");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvestorResponseVM>> getInvestor(@PathVariable long id) {
        InvestorResponseVM response = investorService.getInvestorById(id);
        ApiResponse<InvestorResponseVM> apiResponse = ApiResponse.success(response, "/api/investors");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInvestor(@PathVariable long id) {
        investorService.deleteInvestor(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "/api/investors");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<InvestorResponseVM>>> getAllInvestors() {
        List<InvestorResponseVM> response = investorService.getInvestors();
        ApiResponse<List<InvestorResponseVM>> apiResponse = ApiResponse.success(response, "/api/investors");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
