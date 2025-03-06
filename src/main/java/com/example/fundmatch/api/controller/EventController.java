package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.event.CreateEventRequestDto;
import com.example.fundmatch.domain.vm.EventResponseVM;
import com.example.fundmatch.service.interfaces.EventService;
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
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class EventController{
    private final EventService eventService;
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EventResponseVM>> saveEvent(
            @Valid @ModelAttribute CreateEventRequestDto eventRequest , @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        EventResponseVM response = eventService.saveEvent(eventRequest, file);
        ApiResponse<EventResponseVM> apiResponse = ApiResponse.success(response, "/api/events/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponseVM>> updateEvent(@PathVariable long id , @Valid @RequestBody CreateEventRequestDto eventRequest) {
        EventResponseVM response = eventService.updateEvent(eventRequest, id);
        ApiResponse<EventResponseVM> apiResponse = ApiResponse.success(response, "/api/events");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponseVM>> getEvent(@PathVariable long id) {
        EventResponseVM response = eventService.getEventById(id);
        ApiResponse<EventResponseVM> apiResponse = ApiResponse.success(response, "/api/events");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "/api/events");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<EventResponseVM>>> getAllEvents() {
        List<EventResponseVM> response = eventService.getEvents();
        ApiResponse<List<EventResponseVM>> apiResponse = ApiResponse.success(response, "/api/events");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
