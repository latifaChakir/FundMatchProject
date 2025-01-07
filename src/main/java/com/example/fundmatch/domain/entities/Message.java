package com.example.fundmatch.domain.entities;

import com.example.fundmatch.domain.enums.MessageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    private MessageType type;
}

