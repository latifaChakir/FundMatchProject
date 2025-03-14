package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.feedback.CreateFeedbackRequestDto;
import com.example.fundmatch.domain.vm.FeedbackResponseVM;

import java.util.List;

public interface FeedbackService {
    FeedbackResponseVM addFeedback(Long ProjectId, CreateFeedbackRequestDto feedbackRequest);
    FeedbackResponseVM markFeedbackAsPublic(Long projectId);

    List<FeedbackResponseVM> loadMyFeedbacks();

    List<FeedbackResponseVM> getPublicFeedbacksByProject(Long projectId);

    List<FeedbackResponseVM> getPublicFeedbacksByStartup(Long startupId);
}
