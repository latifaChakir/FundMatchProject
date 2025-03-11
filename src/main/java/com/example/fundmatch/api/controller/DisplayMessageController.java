package com.example.fundmatch.api.controller;

import com.example.fundmatch.domain.vm.MessageResponseVM;
import com.example.fundmatch.service.interfaces.MessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DisplayMessageController {
    private final MessageService messageService;
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageResponseVM>> getUserMessages(@PathVariable Long userId) {
        List<MessageResponseVM> messages = messageService.getUserMessagesByUserId(userId);
        return ResponseEntity.ok(messages);
    }
}
