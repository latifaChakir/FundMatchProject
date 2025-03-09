package com.example.fundmatch.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
        System.out.println("salam");
        messageRequest.setContent(HtmlUtils.htmlEscape(messageRequest.getContent()));
        MessageResponseVM response = messageService.sendMessage(messageRequest, accessor);
        String destination = "/user/" + messageRequest.getReceiverId() + "/queue/messages";
        messagingTemplate.convertAndSendToUser(messageRequest.getReceiverId().toString(), "/queue/messages", response);
        System.out.println("Message envoyé à l'utilisateur ID: " + messageRequest.getReceiverId());
    }

    @MessageMapping("/getMessageById")
    @SendToUser("/queue/messages")
    public MessageResponseVM getMessageById(@Payload Long id) {
        return messageService.getMessageById(id);
    }

    @MessageMapping("/getMessagesBySender")
    @SendToUser("/queue/messages")
    public List<MessageResponseVM> getMessagesBySender(Principal principal) {
        return messageService.getMessagesBySender();
    }

    @MessageMapping("/getMessagesByReceiver")
    @SendToUser("/queue/messages")
    public List<MessageResponseVM> getMessagesByReceiver(Principal principal) {
        return messageService.getMessagesByReceiver();
    }

    @MessageMapping("/markMessageAsRead")
    @SendToUser("/queue/messages")
    public MessageResponseVM markMessageAsRead(@Payload Long id) {
        return messageService.markMessageAsRead(id);
    }

    @MessageMapping("/deleteMessage")
    @SendToUser("/queue/messages")
    public String deleteMessage(@Payload Long id) {
        messageService.deleteMessage(id);
        return "Message deleted successfully";
    }
}
