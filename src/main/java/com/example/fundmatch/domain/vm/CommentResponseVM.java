package com.example.fundmatch.domain.vm;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseVM {
    private Long id;
    private StartupResponseVM startup;
    private UserResponseVM user;
    private String content;
    private LocalDateTime createdAt;
}
