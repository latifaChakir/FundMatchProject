package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.event.CreateEventRequestDto;
import com.example.fundmatch.domain.vm.EventResponseVM;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService{
    EventResponseVM saveEvent(CreateEventRequestDto eventRequest, MultipartFile file) throws IOException;
    EventResponseVM getEventById(Long id);
    EventResponseVM updateEvent(CreateEventRequestDto eventRequest, Long id);
    void deleteEvent(Long id);
    List<EventResponseVM> getEvents();

}
