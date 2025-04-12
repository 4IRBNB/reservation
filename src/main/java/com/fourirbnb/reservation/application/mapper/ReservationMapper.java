package com.fourirbnb.reservation.application.mapper;

import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.domain.model.Reservation;
import com.fourirbnb.reservation.domain.model.ReservationStatus;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ReservationMapper {

  public Reservation toEntity(CreateReservationInternalDto request) {
    return new Reservation(
        request.userId(),
        request.lodgeId(),
        request.price(),
        request.checkInDate(),
        request.checkOutDate(),
        ReservationStatus.RESERVED
    );
  }

  public ReservationResponseInternalDto toResponse(Reservation reservation) {
    return new ReservationResponseInternalDto(
        reservation.getId(),
        reservation.getUserId(),
        reservation.getLodgeId(),
        reservation.getPrice(),
        reservation.getCheckInDate(),
        reservation.getCheckOutDate(),
        reservation.getReservationStatus().getDescription()
    );
  }

  public List<ReservationResponseInternalDto> toResponseList(List<Reservation> reservationList) {
    return reservationList.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public Page<ReservationResponseInternalDto> toResponsePage(Page<Reservation> reservationPage) {
    List<ReservationResponseInternalDto> responseList = reservationPage.getContent().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());

    return new PageImpl<>(responseList, reservationPage.getPageable(), responseList.size());
  }
}
