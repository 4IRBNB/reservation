package com.fourirbnb.reservation.infrastructure.repository;

import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

  private final ReservationJpaRepository jpaRepository;


}
