package com.fourirbnb.reservation.infrastructure.repository;

import com.fourirbnb.reservation.domain.model.Reservation;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

  private final ReservationJpaRepository jpaRepository;

  @Override
  public void save(Reservation reservation) {
    jpaRepository.save(reservation);
  }

  @Override
  public Page<Reservation> findAll(Pageable pageable) {
    return jpaRepository.findAll(pageable);
  }

  @Override
  public Page<Reservation> findAllByUserId(Long userId, Pageable pageable) {
    return jpaRepository.findAllByUserId(userId, pageable);
  }

  @Override
  public Page<Reservation> findAllByLodgeId(UUID lodgeId, Pageable pageable) {
    return jpaRepository.findAllByLodgeId(lodgeId, pageable);
  }

  @Override
  public Optional<Reservation> findById(UUID reservationId) {
    return jpaRepository.findById(reservationId);
  }

  @Override
  public void deleteById(UUID reservationId) {
    jpaRepository.deleteById(reservationId);
  }

  @Override
  public void deleteAll() {
    jpaRepository.deleteAll();
  }
}
