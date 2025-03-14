package com.example.fundmatch.domain.dtos.request.feedback;
import com.example.fundmatch.domain.enums.FeedbackType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateFeedbackRequestDto {
    @NotBlank(message = "Feedback content is required")
    private String content;

    @NotBlank(message = "Feedback type is required")
    private FeedbackType type;

    private Boolean isPrivate;
}
