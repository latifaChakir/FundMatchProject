package com.example.fundmatch.domain.dtos.request.event;

import com.example.fundmatch.domain.enums.EventType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateEventRequestDto {
    @NotBlank(message = "Event title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDate date;

    @NotNull(message = "Event location is required")
    private String location;

    @NotNull(message = "Event cost is required")
    private Double cost;

    @NotBlank(message = "Event type is required")
    private EventType type;

    @NotNull(message = "Maximum number of participants is required")
    private Integer maxParticipants;
}
