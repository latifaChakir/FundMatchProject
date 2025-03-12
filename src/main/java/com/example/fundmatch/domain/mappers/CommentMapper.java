package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.entities.Comment;
import com.example.fundmatch.domain.vm.CommentResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "startup", target = "startup")
    @Mapping(source = "user", target = "user")
    CommentResponseVM toDto(Comment savedComment);
    @Mapping(source = "startup", target = "startup")
    @Mapping(source = "user", target = "user")
    List<CommentResponseVM> toDtoList(List<Comment> comments);
}
