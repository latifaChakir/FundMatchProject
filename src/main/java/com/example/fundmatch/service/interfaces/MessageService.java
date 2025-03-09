package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.message.MessageRequest;
import com.example.fundmatch.domain.vm.MessageResponseVM;

import java.util.List;

public interface MessageService {
    MessageResponseVM sendMessage(MessageRequest messageRequest);

    MessageResponseVM getMessageById(Long id);
    List<MessageResponseVM> getMessagesBySender();
    List<MessageResponseVM> getMessagesByReceiver();
    MessageResponseVM markMessageAsRead(Long id);
    void deleteMessage(Long id);
}
