package com.example.fundmatch.domain.dtos.request.message;

import com.example.fundmatch.domain.enums.MessageType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageRequest {
    private Long receiverId;
    private String content;
    private MessageType type;
}
