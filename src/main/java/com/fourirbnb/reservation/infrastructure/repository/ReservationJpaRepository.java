package com.fourirbnb.reservation.infrastructure.repository;

import com.fourirbnb.reservation.domain.model.Reservation;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {

  Page<Reservation> findAllByUserId(Long userId, Pageable pageable);

  Page<Reservation> findAllByLodgeId(UUID lodgeId, Pageable pageable);
}
