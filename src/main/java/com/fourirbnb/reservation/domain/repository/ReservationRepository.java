package com.fourirbnb.reservation.domain.repository;

import com.fourirbnb.reservation.domain.model.Reservation;

public interface ReservationRepository {

  void save(Reservation reservation);
}
