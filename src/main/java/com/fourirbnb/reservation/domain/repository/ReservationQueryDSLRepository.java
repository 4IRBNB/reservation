package com.fourirbnb.reservation.domain.repository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReservationQueryDSLRepository {

  boolean existsReservation(UUID lodgeId, LocalDateTime checkInDate, LocalDateTime checkOutDate);
}
