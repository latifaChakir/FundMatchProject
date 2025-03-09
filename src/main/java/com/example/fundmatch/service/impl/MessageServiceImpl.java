package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.message.MessageRequest;
import com.example.fundmatch.domain.entities.Message;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.mappers.MessageMapper;
import com.example.fundmatch.domain.vm.MessageResponseVM;
import com.example.fundmatch.repository.MessageRepository;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.service.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageResponseVM sendMessage(MessageRequest messageRequest) {
        System.out.println("ayih ayih");
        String username = getAuthenticatedUsername();
        User sender = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(messageRequest.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        System.out.println("Traitement du message : " + messageRequest);

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(messageRequest.getContent());
        message.setTimestamp(new Date());
        message.setIsRead(false);
        message.setType(messageRequest.getType());

        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDto(savedMessage);
    }

    @Override
    public MessageResponseVM getMessageById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isEmpty()) {
            throw new RuntimeException("Message not found");
        }
        return messageMapper.toDto(message.get());
    }

    @Override
    public List<MessageResponseVM> getMessagesBySender() {
        String username = getAuthenticatedUsername();
        User sender = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        List<Message> messages = messageRepository.findBySenderIdOrderByTimestampDesc(sender.getId());
        return messageMapper.toDtoList(messages);
    }

    @Override
    public List<MessageResponseVM> getMessagesByReceiver() {
        String username = getAuthenticatedUsername();
        User receiver = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        List<Message> messages = messageRepository.findByReceiverIdOrderByTimestampDesc(receiver.getId());
        return messageMapper.toDtoList(messages);
    }

    @Override
    public MessageResponseVM markMessageAsRead(Long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setIsRead(true);
            messageRepository.save(message);
            return messageMapper.toDto(message); // Return the updated message
        } else {
            throw new RuntimeException("Message not found");
        }
    }

    @Override
    public void deleteMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isEmpty()) {
            throw new RuntimeException("Message not found");
        }
        messageRepository.delete(message.get());
    }

    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
