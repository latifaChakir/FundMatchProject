package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.comment.CommentRequest;
import com.example.fundmatch.domain.entities.Startup;
import com.example.fundmatch.domain.vm.CommentResponseVM;
import com.example.fundmatch.domain.vm.StartupResponseVM;
import com.example.fundmatch.domain.vm.UserResponseVM;

import java.util.List;

public interface CommentService {
    StartupResponseVM toggleLike(Long startupId);
    CommentResponseVM addComment(Long startupId, CommentRequest commentRequest);
    List<UserResponseVM> getStartupLikes(Long startupId);
    List<CommentResponseVM> getStartupComments(Long startupId);

}
