package com.fourirbnb.reservation.application.service;

import com.fourirbnb.common.exception.InternalServerException;
import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.application.dto.UpdateReservationInternalDto;
import com.fourirbnb.reservation.domain.model.NotificationData;
import com.fourirbnb.reservation.domain.model.PaymentData;
import com.fourirbnb.reservation.domain.port.NotificationPort;
import com.fourirbnb.reservation.domain.port.PaymentPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

  private final ReservationService reservationService;
  private final PaymentPort paymentPort;
  private final NotificationPort notificationPort;

  public ReservationResponseInternalDto createReservation(CreateReservationInternalDto request) {

    ReservationResponseInternalDto reservation = reservationService.createReservation(request);

    try {
      PaymentData payment = paymentPort.toDomainModel(
          paymentPort.createPayment(reservation.id(), reservation.price(), false)
      );

      reservationService.updateToReserve(reservation.id());

    } catch (Exception e) {

      throw new InternalServerException(e.getMessage());
    }

    try {

      NotificationData notification = notificationPort.toDomainModel(
          notificationPort.createNotification(
              reservation.id(), reservation.userId(), reservation.lodgeId(),
              reservation.checkInDate(), reservation.checkOutDate(),
              reservation.reservationStatus()
          )
      );
    } catch (Exception e) {

      throw new InternalServerException(e.getMessage());
    }

    return reservation;
  }

  public ReservationResponseInternalDto updateReservationStatus(
      UUID reservationId, UpdateReservationInternalDto request) {

    ReservationResponseInternalDto reservation = reservationService
        .updateReservationStatus(reservationId, request);

    try {

      NotificationData notification = notificationPort.toDomainModel(
          notificationPort.createNotification(
              reservation.id(), reservation.userId(), reservation.lodgeId(),
              reservation.checkInDate(), reservation.checkOutDate(),
              reservation.reservationStatus()
          )
      );
    } catch (Exception e) {

      throw new InternalServerException(e.getMessage());
    }

    return reservation;
  }
}
