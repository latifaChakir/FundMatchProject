package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.comment.CommentRequest;
import com.example.fundmatch.domain.entities.Comment;
import com.example.fundmatch.domain.entities.Startup;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.mappers.CommentMapper;
import com.example.fundmatch.domain.mappers.StartupMapper;
import com.example.fundmatch.domain.mappers.UserMapper;
import com.example.fundmatch.domain.vm.CommentResponseVM;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import com.example.fundmatch.domain.vm.UserResponseVM;
import com.example.fundmatch.repository.CommentRepository;
import com.example.fundmatch.repository.StartupRepository;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.security.CustomUserDetails;
import com.example.fundmatch.service.interfaces.CommentService;
import com.example.fundmatch.shared.exception.StartupNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final StartupRepository startupRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final StartupMapper startupMapper;
    private final UserMapper userMapper;

    @Override
    public StartupResponseVM toggleLike(Long startupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails");
        }
        Long userId = userDetails.getUserId();

        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new StartupNotFoundException("Startup not found."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        if (!startup.getLikes().removeIf(u -> u.getId().equals(user.getId()))) {
            startup.getLikes().add(user);
        }

        startupRepository.save(startup);
        return startupMapper.toDto(startup);
    }

    @Override
    public CommentResponseVM addComment(Long startupId, CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Authentication principal is not of type CustomUserDetails");
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        Long userId = userDetails.getUserId();
        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new StartupNotFoundException("Startup not found."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        Comment comment = new Comment();
        comment.setStartup(startup);
        comment.setUser(user);
        comment.setContent(commentRequest.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    @Override
    public List<UserResponseVM> getStartupLikes(Long startupId) {
        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new StartupNotFoundException("Startup not found with ID: " + startupId));

        Set<User> likes = startup.getLikes();

        return userMapper.toDtoList(likes);
    }

    @Override
    public List<CommentResponseVM> getStartupComments(Long startupId) {
        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new StartupNotFoundException("Startup not found with ID: " + startupId));

        List<Comment> comments = startup.getComments();

        return commentMapper.toDtoList(comments);
    }
}
