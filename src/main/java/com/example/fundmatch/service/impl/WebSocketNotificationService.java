package com.example.fundmatch.service.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendEventPublishedNotification(String eventId) {
        System.out.println("getEventPublishedNotification");
        messagingTemplate.convertAndSend("/topic/events", "Event published: " + eventId);
    }
}

