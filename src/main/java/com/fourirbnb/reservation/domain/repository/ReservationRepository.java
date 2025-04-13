package com.fourirbnb.reservation.domain.repository;

import com.fourirbnb.reservation.domain.model.Reservation;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepository {

  void save(Reservation reservation);

  Page<Reservation> findAll(Pageable pageable);

  Page<Reservation> findAllByUserId(Long userId, Pageable pageable);

  Page<Reservation> findAllByLodgeId(UUID lodgeId, Pageable pageable);

  void deleteAll();

  Optional<Reservation> findById(UUID reservationId);
}
