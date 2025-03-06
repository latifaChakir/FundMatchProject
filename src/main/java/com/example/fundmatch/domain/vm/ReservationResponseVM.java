package com.example.fundmatch.domain.vm;

import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.enums.ReservationStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReservationResponseVM {
    private Long id;
    private EventResponseVM event;
    private UserResponseVM user;
    private ReservationStatus status;
    private String reservationDate;
}
