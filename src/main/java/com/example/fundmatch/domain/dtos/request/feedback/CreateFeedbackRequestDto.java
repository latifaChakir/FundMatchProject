package com.example.fundmatch.domain.dtos.request.feedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateFeedbackRequestDto {
    @NotNull(message = "Investor ID is required")
    private Long investorId;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotBlank(message = "Feedback content is required")
    private String content;

    @NotBlank(message = "Feedback type is required")
    private String type;

    private Boolean isPrivate;
}
