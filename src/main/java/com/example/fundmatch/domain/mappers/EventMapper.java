package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.event.CreateEventRequestDto;
import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.vm.EventResponseVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(CreateEventRequestDto dto);
    EventResponseVM toDto(Event event);
    List<EventResponseVM> toDtoList(List<Event> events);
}

