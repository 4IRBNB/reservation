package com.fourirbnb.reservation.presentation.controller;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.application.port.message.PaymentCancelRequestPublisher;
import com.fourirbnb.reservation.application.service.ReservationMessageService;
import com.fourirbnb.reservation.presentation.dto.CreateReservationDto;
import com.fourirbnb.reservation.presentation.dto.UpdateReservationDto;
import com.fourirbnb.reservation.presentation.mapper.ReservationDtoMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/reservations")
@RequiredArgsConstructor
public class ReservationControllerV2 {

  private final ReservationMessageService reservationMessageService;
  private final PaymentCancelRequestPublisher paymentCancelRequestPublisher;

  @PostMapping("")
  public BaseResponse<Void> createReservation(@RequestBody CreateReservationDto dto) {
    reservationMessageService.createReservation(ReservationDtoMapper.toCreateInternalDto(dto));
    return BaseResponse.SUCCESS(
        null, "Reservation created", HttpStatus.CREATED.value()
    );
  }

  @PatchMapping("/{reservationId}")
  public BaseResponse<Void> updateReservation(
      @PathVariable UUID reservationId, @RequestBody UpdateReservationDto dto) {

    reservationMessageService.updateReservation(
        reservationId, ReservationDtoMapper.toUpdateInternalDto(dto)
    );
    return BaseResponse.SUCCESS(null, "Reservation updated", HttpStatus.OK.value());
  }
}
