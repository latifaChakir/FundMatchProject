package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.message.MessageRequest;
import com.example.fundmatch.domain.entities.Message;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.mappers.MessageMapper;
import com.example.fundmatch.domain.vm.MessageResponseVM;
import com.example.fundmatch.repository.MessageRepository;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.security.JwtService;
import com.example.fundmatch.service.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public MessageResponseVM sendMessage(MessageRequest messageRequest, StompHeaderAccessor accessor) {
        UserDetails user = getAuthenticatedUser(accessor);
        if (user == null) {
            throw new RuntimeException("Utilisateur non authentifié !");
        }

        User sender = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Expéditeur non trouvé"));
        User receiver = userRepository.findById(messageRequest.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));

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
    public List<MessageResponseVM> getMessagesBySender(Long id) {
        User sender = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        List<Message> messages = messageRepository.findBySenderIdOrderByTimestampDesc(sender.getId());
        return messageMapper.toDtoList(messages);
    }

    @Override
    public List<MessageResponseVM> getMessagesByReceiver(Long id) {
        User receiver = userRepository.findById(id)
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

    public UserDetails getAuthenticatedUser(StompHeaderAccessor accessor) {
        // Try to get from Security Context first (most reliable)
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        // Fallback to header extraction
        List<String> authHeader = accessor.getNativeHeader("Authorization");
        if (authHeader != null && !authHeader.isEmpty()) {
            String token = authHeader.get(0);
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                try {
                    String username = jwtService.extractEmail(jwt);
                    if (username != null) {
                        return userDetailsService.loadUserByUsername(username);
                    }
                } catch (Exception e) {
                    System.out.println("Error extracting user from JWT: " + e.getMessage());
                }
            }
        }

        // Check if user is directly in accessor
        if (accessor.getUser() != null && accessor.getUser().getName() != null) {
            return userDetailsService.loadUserByUsername(accessor.getUser().getName());
        }

        System.out.println("No authenticated user found in request");
        return null;
    }
    @Override
    public List<MessageResponseVM> getUserMessages(StompHeaderAccessor accessor) {
        System.out.println("Allah alllah ");
        UserDetails user = getAuthenticatedUser(accessor);
        if (user == null) {
            throw new RuntimeException("Utilisateur non authentifié !");
        }

        User authenticatedUser = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Message> sentMessages = messageRepository.findBySenderIdOrderByTimestampDesc(authenticatedUser.getId());
        List<Message> receivedMessages = messageRepository.findByReceiverIdOrderByTimestampDesc(authenticatedUser.getId());
        System.out.println("sentMessages"+sentMessages);
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(receivedMessages);

        // Trier par date (ordre descendant)
        allMessages.sort(Comparator.comparing(Message::getTimestamp).reversed());

        return messageMapper.toDtoList(allMessages);
    }

    @Override
    public List<MessageResponseVM> getUserMessagesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Message> sentMessages = messageRepository.findBySenderIdOrderByTimestampDesc(userId);
        List<Message> receivedMessages = messageRepository.findByReceiverIdOrderByTimestampDesc(userId);

        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(receivedMessages);

        // Sort by date (descending order)
        allMessages.sort(Comparator.comparing(Message::getTimestamp).reversed());

        return messageMapper.toDtoList(allMessages);
    }


}
