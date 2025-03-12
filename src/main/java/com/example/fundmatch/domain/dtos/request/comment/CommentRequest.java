package com.example.fundmatch.domain.dtos.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentRequest {
    @NotBlank(message = "Comment content cannot be blank.")
    private String content;
}
