package com.example.fundmatch.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller

public class NotificationMessageController {
    @MessageMapping("/message-notifications")
    @SendTo("/topic/messages")
    public String notifyMessagePublished(String message) {
        System.out.println("Notification messages: " + message);
        return message;
    }
}
