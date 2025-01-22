package com.example.fundmatch.api.controller;

import com.example.fundmatch.api.wrapper.ApiResponse;
import com.example.fundmatch.domain.dtos.request.event.CreateReservationRequestDto;
import com.example.fundmatch.domain.vm.ReservationResponseVM;
import com.example.fundmatch.service.interfaces.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Reservations")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<ReservationResponseVM>> saveReservation(@Valid @RequestBody CreateReservationRequestDto reservationRequest) {
        ReservationResponseVM response = reservationService.saveReservation(reservationRequest);
        ApiResponse<ReservationResponseVM> apiResponse = ApiResponse.success(response, "/api/reservations/save");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationResponseVM>> updateReservation(@PathVariable long id , @Valid @RequestBody CreateReservationRequestDto reservationRequest) {
        ReservationResponseVM response = reservationService.updateReservation(reservationRequest, id);
        ApiResponse<ReservationResponseVM> apiResponse = ApiResponse.success(response, "/api/Reservations");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationResponseVM>> getReservation(@PathVariable long id) {
        ReservationResponseVM response = reservationService.getReservationById(id);
        ApiResponse<ReservationResponseVM> apiResponse = ApiResponse.success(response, "/api/reservations");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(null, "/api/reservations");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponseVM>>> getAllReservations() {
        List<ReservationResponseVM> response = reservationService.getReservations();
        ApiResponse<List<ReservationResponseVM>> apiResponse = ApiResponse.success(response, "/api/reservations");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
