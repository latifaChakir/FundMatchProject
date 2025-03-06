package com.example.fundmatch.domain.mappers;

import com.example.fundmatch.domain.entities.Reservation;
import com.example.fundmatch.domain.vm.ReservationResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ReservationMapper {
    ReservationResponseVM toDto(Reservation savedReservation);
    List<ReservationResponseVM> toDtoList(List<Reservation> reservations);
}
