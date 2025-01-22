// EventResponseVM
package com.example.fundmatch.domain.vm;

import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.enums.EventType;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseVM {
    private Long id;
    private String title;
    private String description;
    private Date date;
    private String location;
    private Double cost;
    private EventType type;
    private Integer maxParticipants;
}
