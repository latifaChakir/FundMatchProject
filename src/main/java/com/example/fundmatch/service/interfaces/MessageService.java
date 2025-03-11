package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.message.MessageRequest;
import com.example.fundmatch.domain.vm.MessageResponseVM;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

public interface MessageService {
    MessageResponseVM sendMessage(MessageRequest messageRequest, StompHeaderAccessor accessor);

    MessageResponseVM getMessageById(Long id);
    List<MessageResponseVM> getMessagesBySender(Long id);
    List<MessageResponseVM> getMessagesByReceiver(Long id);
    MessageResponseVM markMessageAsRead(Long id);
    void deleteMessage(Long id);
    List<MessageResponseVM> getUserMessages(StompHeaderAccessor accessor);
    List<MessageResponseVM> getUserMessagesByUserId(Long userId);

}
