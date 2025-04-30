package com.fourirbnb.reservation.application.service;

import com.fourirbnb.common.exception.InvalidParameterException;
import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.application.dto.UpdateReservationInternalDto;
import com.fourirbnb.reservation.application.event.PaymentCancelResponseEvent;
import com.fourirbnb.reservation.application.event.PaymentRequestEvent;
import com.fourirbnb.reservation.application.event.PaymentResponseEvent;
import com.fourirbnb.reservation.application.port.message.PaymentCancelRequestPublisher;
import com.fourirbnb.reservation.application.port.message.PaymentRequestPublisher;
import com.fourirbnb.reservation.domain.model.Reservation;
import com.fourirbnb.reservation.domain.model.ReservationStatus;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationMessageService {

  private final ReservationRepository reservationRepository;
  private final ReservationService reservationService;
  private final PaymentRequestPublisher paymentRequestPublisher;
  private final PaymentCancelRequestPublisher cancelRequestPublisher;

  @Transactional
  @CacheEvict(value = "lodgeReservations", allEntries = true)
  public void createReservation(CreateReservationInternalDto dto) {

    ReservationResponseInternalDto reservation = reservationService.createReservation(dto);

    PaymentRequestEvent event =
        new PaymentRequestEvent(reservation.id(), reservation.price(), false);

    paymentRequestPublisher.publish(event);
  }

  @Transactional
  @CacheEvict(value = "lodgeReservations", allEntries = true)
  public void updateReservation(UUID reservationId, UpdateReservationInternalDto dto) {
    Reservation reservation = reservationService.findReservationById(reservationId);

    if (dto.reservationStatus().equals(ReservationStatus.COMPLETED.getStatus())) {
      reservation.update(ReservationStatus.valueOf(dto.reservationStatus()));
      reservationRepository.save(reservation);
    } else if (dto.reservationStatus().equals(ReservationStatus.CANCELLED.getStatus())) {
      reservation.cancel();
      reservationRepository.save(reservation);
      cancelRequestPublisher.publish(reservationId);
    } else {
      throw new InvalidParameterException("잘못된 요청입니다");
    }
  }

  @Transactional
  @CacheEvict(value = "lodgeReservations", allEntries = true)
  public void updateStatusFromPayment(PaymentResponseEvent event) {
    Reservation reservation = reservationService.findReservationById(event.reservationId());

    if (!event.success()) {
      reservation.cancel();
    } else {
      reservation.reserve();
    }
    reservationRepository.save(reservation);
  }

  @Transactional
  @CacheEvict(value = "lodgeReservations", allEntries = true)
  public void updateStatusFromCancelResponse(PaymentCancelResponseEvent event) {
    if (event.cancelled()) {
      Reservation reservation = reservationService.findReservationById(event.reservationId());
      reservation.cancel();
      reservationRepository.save(reservation);
    }
  }
}
