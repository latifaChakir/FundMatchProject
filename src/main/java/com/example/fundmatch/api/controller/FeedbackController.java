package com.example.fundmatch.api.controller;

import com.example.fundmatch.domain.dtos.request.feedback.CreateFeedbackRequestDto;
import com.example.fundmatch.domain.vm.FeedbackResponseVM;
import com.example.fundmatch.service.interfaces.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/project/{projectId}")
    public ResponseEntity<FeedbackResponseVM> addFeedback(
            @PathVariable Long projectId,
            @RequestBody CreateFeedbackRequestDto feedbackRequest) {
        FeedbackResponseVM response = feedbackService.addFeedback(projectId, feedbackRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{feedbackId}/public")
    public ResponseEntity<FeedbackResponseVM> markFeedbackAsPublic(@PathVariable Long feedbackId) {
        FeedbackResponseVM response = feedbackService.markFeedbackAsPublic(feedbackId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/myFeedbacks")
    public List<FeedbackResponseVM> getMyFeedbacks() {
        return feedbackService.loadMyFeedbacks();
    }
    @GetMapping("/getPublicFeedbacks/{projectId}")
    public List<FeedbackResponseVM> getPublicFeedbacksByProject(@PathVariable Long projectId) {
        return feedbackService.getPublicFeedbacksByProject(projectId);
    }
}

