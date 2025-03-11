package com.example.fundmatch.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.fundmatch.domain.dtos.request.message.MessageRequest;
import com.example.fundmatch.domain.vm.MessageResponseVM;
import com.example.fundmatch.service.interfaces.MessageService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/app/sendMessage")
    public void sendMessage(@Payload MessageRequest messageRequest, StompHeaderAccessor accessor) {
        messageRequest.setContent(HtmlUtils.htmlEscape(messageRequest.getContent())); // Sanitize content
        MessageResponseVM response = messageService.sendMessage(messageRequest, accessor);
        String destination = "/user/" + messageRequest.getReceiverId() + "/queue/messages";

        // Ajoutez ce log pour voir ce qui est envoyé
        System.out.println("✅ Envoi du message à la destination: " + destination);

        messagingTemplate.convertAndSendToUser(messageRequest.getReceiverId().toString(), "/queue/messages", response);
    }


    @MessageMapping("/app/getMessageById")
    @SendToUser("/queue/messages")
    public MessageResponseVM getMessageById(@Payload Long id) {
        return messageService.getMessageById(id);
    }

    @MessageMapping("/app/getMessagesBySender")
    @SendToUser("/queue/messages")
    public List<MessageResponseVM> getMessagesBySender(Principal principal) {
        Long senderId = Long.parseLong(principal.getName()); // Get the connected user's ID
        return messageService.getMessagesBySender(senderId);
    }

    @MessageMapping("/app/getMessagesByReceiver")
    @SendToUser("/queue/messages")
    public List<MessageResponseVM> getMessagesByReceiver(Principal principal) {
        Long receiverId = Long.parseLong(principal.getName()); // Get the connected user's ID
        return messageService.getMessagesByReceiver(receiverId);
    }

    @MessageMapping("/app/markMessageAsRead")
    @SendToUser("/queue/messages")
    public MessageResponseVM markMessageAsRead(@Payload Long id) {
        return messageService.markMessageAsRead(id);
    }

    @MessageMapping("/app/deleteMessage")
    @SendToUser("/queue/messages")
    public String deleteMessage(@Payload Long id) {
        messageService.deleteMessage(id);
        return "Message deleted successfully";
    }

    @MessageMapping("/app/getUserMessages")
    @SendToUser("/queue/messages")
    public List<MessageResponseVM> getUserMessages(StompHeaderAccessor accessor) {
        System.out.println("pour afficher les messages");
        return messageService.getUserMessages(accessor);
    }
}
