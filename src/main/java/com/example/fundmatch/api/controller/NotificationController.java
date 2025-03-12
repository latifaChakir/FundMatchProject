package com.example.fundmatch.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @MessageMapping("/notifications")
    @SendTo("/topic/events")
    public String notifyEventPublished(String message) {
        System.out.println("Notification ACHAAA");
        return message;
    }
}
