package com.example.fundmatch.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class InfoController {

    @GetMapping("/ws/info")
    public String getInfo() {
        return "WebSocket is active and ready to use.";
    }
}
