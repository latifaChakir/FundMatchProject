package com.example.fundmatch.api.controller;

import com.example.fundmatch.domain.dtos.request.comment.CommentRequest;
import com.example.fundmatch.domain.vm.CommentResponseVM;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import com.example.fundmatch.domain.vm.UserResponseVM;
import com.example.fundmatch.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{startupId}/like")
    public StartupResponseVM likeStartup(@PathVariable Long startupId) {
        return commentService.toggleLike(startupId);
    }

    @PostMapping("/{startupId}/comment")
    public CommentResponseVM commentStartup(@PathVariable Long startupId, @RequestBody CommentRequest commentRequest) {
        return commentService.addComment(startupId, commentRequest);
    }
    @GetMapping("/{startupId}/comments")
    public List<CommentResponseVM> getAllComments(@PathVariable Long startupId) {
        return commentService.getStartupComments(startupId);
    }
    @GetMapping("/{startupId}/likes")
    public List<UserResponseVM> getLikeCount(@PathVariable Long startupId) {
        return commentService.getStartupLikes(startupId);
    }
}
