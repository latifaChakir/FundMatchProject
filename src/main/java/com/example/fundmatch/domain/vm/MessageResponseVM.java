package com.example.fundmatch.domain.vm;

import com.example.fundmatch.domain.enums.MessageType;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponseVM {
    private Long id;
    private UserResponseVM sender;
    private UserResponseVM receiver;
    private String content;
    private Date timestamp;
    private Boolean isRead;
    private MessageType type;
}
