package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.feedback.CreateFeedbackRequestDto;
import com.example.fundmatch.domain.vm.FeedbackResponseVM;

public interface FeedbackService {
    FeedbackResponseVM addFeedback(Long ProjectId, CreateFeedbackRequestDto feedbackRequest);
    FeedbackResponseVM markFeedbackAsPublic(Long projectId);

}
