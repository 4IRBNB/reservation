package com.fourirbnb.reservation.domain.service;

import com.fourirbnb.common.exception.DuplicateResourceException;
import com.fourirbnb.reservation.domain.model.Reservation;
import com.fourirbnb.reservation.domain.model.ReservationStatus;
import com.fourirbnb.reservation.domain.repository.ReservationQueryDSLRepository;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationDomainService {

  private final ReservationRepository reservationRepository;
  private final ReservationQueryDSLRepository reservationQueryDSLRepository;

  public void validateLodgeAvailable(Reservation reservation) {
    if (reservationQueryDSLRepository.existsReservation(
        reservation.getLodgeId(), reservation.getCheckInDate(), reservation.getCheckOutDate()
    )) {
      throw new DuplicateResourceException("예약을 생성할 수 없습니다. : 이미 예약된 객실");
    }
  }
}
