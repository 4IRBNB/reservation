package com.fourirbnb.reservation.presentation.mapper;

import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.application.dto.UpdateReservationInternalDto;
import com.fourirbnb.reservation.presentation.dto.CreateReservationDto;
import com.fourirbnb.reservation.presentation.dto.ReservationResponseDto;
import com.fourirbnb.reservation.presentation.dto.UpdateReservationDto;
import java.util.List;
import java.util.stream.Collectors;


public class ReservationDtoMapper {

  public static CreateReservationInternalDto toCreateInternalDto(CreateReservationDto request) {
    return new CreateReservationInternalDto(
        request.userId(),
        request.lodgeId(),
        request.price(),
        request.checkInDate(),
        request.checkOutDate()
    );
  }

  public UpdateReservationInternalDto toUpdateInternalDto(UpdateReservationDto request) {
    return new UpdateReservationInternalDto(
        request.reservationStatus()
    );
  }

  public static ReservationResponseDto toResponse(
      ReservationResponseInternalDto response) {
    return new ReservationResponseDto(
        response.id(),
        response.userId(),
        response.lodeId(),
        response.price(),
        response.checkInDate(),
        response.checkOutDate(),
        response.reservationStatus()
    );
  }

  public static List<ReservationResponseDto> toResponseList(
      List<ReservationResponseInternalDto> responseList) {
    return responseList.stream()
        .map(ReservationDtoMapper::toResponse)
        .collect(Collectors.toList());
  }
}
