package com.example.fundmatch.api.controller;

import com.example.fundmatch.service.impl.ZoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/zoom")
public class ZoomController {

    private final ZoomService zoomService;

    public ZoomController(ZoomService zoomService) {
        this.zoomService = zoomService;
    }

    @PostMapping("/create-meeting")
    public ResponseEntity<?> createMeeting(@RequestBody Map<String, String> requestData) {
        String topic = requestData.get("topic"); // Récupération du "topic"

        if (topic == null || topic.isEmpty()) {
            return ResponseEntity.badRequest().body("Le paramètre 'topic' est requis.");
        }

        Map<String, Object> meeting = zoomService.createMeeting(topic, LocalDateTime.now().plusDays(1), 60);
        return ResponseEntity.ok(meeting);
    }
}

