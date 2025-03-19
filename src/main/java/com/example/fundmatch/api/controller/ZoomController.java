package com.example.fundmatch.api.controller;

import com.example.fundmatch.domain.entities.MeetingJoin;
import com.example.fundmatch.domain.enums.MeetingStatus;
import com.example.fundmatch.domain.enums.MeetingType;
import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.service.impl.ZoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        MeetingType type = MeetingType.valueOf((String) payload.get("type"));
        MeetingStatus status = MeetingStatus.SCHEDULED;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails");
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        String createdBy = userDetails.getUsername();

        LocalDateTime startDateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Map<String, Object> meeting = zoomService.createMeetingJoin(topic, startDateTime, duration, createdBy,type, status);
        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/myMeetings")
    public List<MeetingJoin> getUserMeetings(){
        return zoomService.getUserMeetings();
    }
}

