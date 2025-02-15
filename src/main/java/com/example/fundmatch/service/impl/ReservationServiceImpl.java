package com.example.fundmatch.service.impl;

import com.example.fundmatch.domain.dtos.request.event.CreateReservationRequestDto;
import com.example.fundmatch.domain.entities.Event;
import com.example.fundmatch.domain.entities.Reservation;
import com.example.fundmatch.domain.entities.User;
import com.example.fundmatch.domain.enums.ReservationStatus;
import com.example.fundmatch.domain.mappers.ReservationMapper;
import com.example.fundmatch.domain.vm.ReservationResponseVM;
import com.example.fundmatch.repository.EventRepository;
import com.example.fundmatch.repository.ReservationRepository;
import com.example.fundmatch.repository.UserRepository;
import com.example.fundmatch.service.interfaces.ReservationService;
import com.example.fundmatch.shared.exception.EventNotFoundException;
import com.example.fundmatch.shared.exception.ReservationLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseVM saveReservation(CreateReservationRequestDto requestDto) {
        Event event = eventRepository.findById(requestDto.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (reservationRepository.countByEventId(event.getId()) >= event.getMaxParticipants()) {
            throw new ReservationLimitExceededException("Event is fully booked.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setEvent(event);
        reservation.setReservationDate(new Date());
        reservation.setStatus(ReservationStatus.ATTEMPTED);

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toDto(savedReservation);
    }

    @Override
    public ReservationResponseVM getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Reservation not found"));
        return reservationMapper.toDto(reservation);
    }

    @Override
    public ReservationResponseVM updateReservation(CreateReservationRequestDto reservationRequest, Long id) {
        return null;
    }

    public List<ReservationResponseVM> getReservationsForEvent(Long eventId) {
        List<Reservation> reservations = reservationRepository.findByEventId(eventId);
        return reservationMapper.toDtoList(reservations);
    }

    @Override
    public List<ReservationResponseVM> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservationMapper.toDtoList(reservations);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }
}
