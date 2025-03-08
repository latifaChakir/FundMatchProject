package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.entities.Message;
import com.example.fundmatch.domain.vm.MessageResponseVM;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")

public interface MessageMapper {
    MessageResponseVM toDto(Message savedMessage);

    List<MessageResponseVM> toDtoList(List<Message> messages);
}
