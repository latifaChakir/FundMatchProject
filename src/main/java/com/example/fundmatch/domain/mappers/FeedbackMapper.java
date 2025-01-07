package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.dtos.request.feedback.CreateFeedbackRequestDto;
import com.example.fundmatch.domain.entities.Feedback;
import com.example.fundmatch.domain.vm.FeedbackResponseVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    Feedback toEntity(CreateFeedbackRequestDto dto);
    FeedbackResponseVM toDto(Feedback feedback);
    List<FeedbackResponseVM> toDtoList(List<Feedback> feedbacks);
}

