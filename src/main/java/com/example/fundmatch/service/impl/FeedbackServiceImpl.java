package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.feedback.CreateFeedbackRequestDto;
import com.example.fundmatch.domain.entities.Feedback;
import com.example.fundmatch.domain.entities.Investor;
import com.example.fundmatch.domain.entities.Project;
import com.example.fundmatch.domain.enums.FeedbackType;
import com.example.fundmatch.domain.mappers.FeedbackMapper;
import com.example.fundmatch.domain.vm.FeedbackResponseVM;
import com.example.fundmatch.repository.FeedbackRepository;
import com.example.fundmatch.repository.InvestorRepository;
import com.example.fundmatch.repository.ProjectRepository;
import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.service.interfaces.FeedbackService;
import com.example.fundmatch.shared.exception.ProjectNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;
    private final ProjectRepository projectRepository;
    private final InvestorRepository investorRepository;


    @Override
    public FeedbackResponseVM addFeedback(Long projectId, CreateFeedbackRequestDto feedbackRequest) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with ID " + projectId + " not found."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails.");
        }

        CustomUserDetails userDetails = (CustomUserDetails) principal;
        Long userId = userDetails.getUserId();
        Investor investor = investorRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Investor not found for User ID " + userId + "."));

        Feedback feedback = new Feedback();
        feedback.setProject(project);
        feedback.setInvestor(investor);
        feedback.setContent(feedbackRequest.getContent());
        feedback.setCreatedAt(new Date());
        feedback.setType(FeedbackType.NEUTRAL);
        feedback.setIsPrivate(feedbackRequest.getIsPrivate());
        Feedback savedFeedback = feedbackRepository.save(feedback);

        return feedbackMapper.toDto(savedFeedback);
    }

    @Override
    public FeedbackResponseVM markFeedbackAsPublic(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        feedback.setIsPrivate(false);
        Feedback updatedFeedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDto(updatedFeedback);
    }

}
