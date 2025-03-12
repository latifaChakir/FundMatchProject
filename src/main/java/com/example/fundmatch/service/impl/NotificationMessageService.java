package com.example.fundmatch.service.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationMessageService {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationMessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageNotification(String message) {
        System.out.println("getEventPublishedNotification");
        messagingTemplate.convertAndSend("/topic/messages", "New Message: " + message);
    }
}
