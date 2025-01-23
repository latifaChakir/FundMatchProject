package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.event.CreateEventRequestDto;
import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.enums.EventType;
import com.example.fundmatch.domain.mappers.EventMapper;
import com.example.fundmatch.domain.vm.EventResponseVM;
import com.example.fundmatch.repository.EventRepository;
import com.example.fundmatch.service.interfaces.EventService;
import com.example.fundmatch.shared.exception.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponseVM saveEvent(CreateEventRequestDto eventRequest) {
        System.out.println(eventRequest);
        Event event = eventMapper.toEntity(eventRequest);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }

    @Override
    public EventResponseVM getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new EventNotFoundException("Event not found.");
        }
        return eventMapper.toDto(event.get());
    }

    @Override
    public EventResponseVM updateEvent(CreateEventRequestDto eventRequest, Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException("Event not found.");
        }

        Event existingEvent = optionalEvent.get();
        existingEvent.setTitle(eventRequest.getTitle());
        existingEvent.setDescription(eventRequest.getDescription());
        existingEvent.setDate(java.sql.Date.valueOf(eventRequest.getDate()));
        existingEvent.setLocation(eventRequest.getLocation());
        existingEvent.setCost(eventRequest.getCost());
        existingEvent.setType(EventType.valueOf(eventRequest.getType().name()));
        existingEvent.setMaxParticipants(eventRequest.getMaxParticipants());

        Event updatedEvent = eventRepository.save(existingEvent);
        return eventMapper.toDto(updatedEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new EventNotFoundException("Event not found.");
        }
        eventRepository.delete(event.get());
    }

    @Override
    public List<EventResponseVM> getEvents() {
        List<Event> events = eventRepository.findAll();
        return eventMapper.toDtoList(events);
    }
}
