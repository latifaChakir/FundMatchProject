package com.example.fundmatch.service.interfaces;

import com.example.fundmatch.domain.dtos.request.event.CreateReservationRequestDto;
import com.example.fundmatch.domain.vm.ReservationResponseVM;

import java.security.Principal;
import java.util.List;

public interface ReservationService {
    ReservationResponseVM saveReservation(CreateReservationRequestDto requestDto, Principal principal);

    ReservationResponseVM getReservationById(Long id);
    ReservationResponseVM updateReservation(CreateReservationRequestDto reservationRequest, Long id);
    void deleteReservation(Long id);
    List<ReservationResponseVM> getReservations();

}
