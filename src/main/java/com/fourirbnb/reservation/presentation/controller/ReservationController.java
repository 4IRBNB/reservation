package com.fourirbnb.reservation.presentation.controller;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.application.service.ReservationService;
import com.fourirbnb.reservation.presentation.dto.CreateReservationDto;
import com.fourirbnb.reservation.presentation.dto.ReservationResponseDto;
import com.fourirbnb.reservation.presentation.mapper.ReservationDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping("")
  public BaseResponse<ReservationResponseDto> createReservation(
      @RequestBody CreateReservationDto request) {

    CreateReservationInternalDto internalDto = ReservationDtoMapper.toCreateInternalDto(request);

    ReservationResponseInternalDto response = reservationService.createReservation(internalDto);

    return BaseResponse.SUCCESS(ReservationDtoMapper.toResponse(response), "예약 생성 성공",
        HttpStatus.CREATED.value());
  }
}
