package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.event.CreateEventRequestDto;
import com.example.fundmatch.domain.vm.EventResponseVM;

import java.util.List;

public interface EventService{
    EventResponseVM saveEvent(CreateEventRequestDto eventRequest);
    EventResponseVM getEventById(Long id);
    EventResponseVM updateEvent(CreateEventRequestDto eventRequest, Long id);
    void deleteEvent(Long id);
    List<EventResponseVM> getEvents();

}
