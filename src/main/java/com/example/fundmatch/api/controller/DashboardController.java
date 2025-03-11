package com.example.fundmatch.api.controller;

import com.example.fundmatch.domain.vm.StatisticResponseVM;
import com.example.fundmatch.service.interfaces.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DashboardController {
    private final StatisticService statisticService;
    @GetMapping("/all")
    public ResponseEntity<StatisticResponseVM> getStatistics() {
        StatisticResponseVM statistics = statisticService.getStatistics();
        return ResponseEntity.ok(statistics);
    }
}
