package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.feedback.CreateFeedbackRequestDto;
import com.example.fundmatch.domain.entities.Feedback;
import com.example.fundmatch.domain.vm.FeedbackResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);
    Feedback toEntity(CreateFeedbackRequestDto dto);
    @Mapping(source = "project", target = "project")
    FeedbackResponseVM toDto(Feedback feedback);
    List<FeedbackResponseVM> toDtoList(List<Feedback> feedbacks);

}

