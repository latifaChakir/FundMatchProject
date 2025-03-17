package com.example.fundmatch.api.controller;

import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.service.impl.ZoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/zoom")
public class ZoomController {

    private final ZoomService zoomService;

    public ZoomController(ZoomService zoomService) {
        this.zoomService = zoomService;
    }

    @PostMapping("/create-meeting")
    public ResponseEntity<Map<String, Object>> createMeeting(@RequestBody Map<String, Object> payload) {
        String topic = (String) payload.get("topic");
        String startTime = (String) payload.get("startTime");
        int duration = (int) payload.get("duration");

        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails");
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        String createdBy = userDetails.getUsername();

        // Convertir startTime en LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Map<String, Object> meeting = zoomService.createMeetingJoin(topic, startDateTime, duration, createdBy);
        return ResponseEntity.ok(meeting);
    }
}

