package com.fourirbnb.reservation.infrastructure.repository;

import com.fourirbnb.reservation.domain.model.Reservation;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

  private final ReservationJpaRepository jpaRepository;

  @Override
  public void save(Reservation reservation) {
    jpaRepository.save(reservation);
  }
}
