package com.example.fundmatch.domain.dtos.request.event;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateReservationRequestDto {
    private Long eventId;
    private String PaymentToken;
}
