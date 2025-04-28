package com.fourirbnb.reservation.application.service;

import com.fourirbnb.reservation.application.event.PaymentResponseEvent;
import com.fourirbnb.reservation.domain.model.Reservation;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationMessageService {

  private final ReservationRepository reservationRepository;
  private final ReservationService reservationService;

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
}
