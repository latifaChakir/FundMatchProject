package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.event.CreateEventRequestDto;
import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.vm.EventResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "imagePath", target = "imagePath", qualifiedByName = "mapImagePath")
    Event toEntity(CreateEventRequestDto dto);
    EventResponseVM toDto(Event event);
    List<EventResponseVM> toDtoList(List<Event> events);

    @Named("mapImagePath")
    default String mapImagePath(MultipartFile file) {
        return file != null ? file.getOriginalFilename() : null;
    }

    @Named("mapImagePathFromEntity")
    default String mapImagePathFromEntity(String imagePath) {
        return imagePath != null ? "http://localhost:9091/api/files/" + imagePath : null;
    }
}

