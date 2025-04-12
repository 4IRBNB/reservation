package com.fourirbnb.reservation.infrastructure.repository;

import com.fourirbnb.reservation.domain.model.Reservation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {

}
