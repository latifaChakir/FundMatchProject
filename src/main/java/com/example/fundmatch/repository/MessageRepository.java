package com.example.fundmatch.repository;

import com.example.fundmatch.domain.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdOrderByTimestampDesc(Long id);
    List<Message> findByReceiverIdOrderByTimestampDesc(Long id);
}
