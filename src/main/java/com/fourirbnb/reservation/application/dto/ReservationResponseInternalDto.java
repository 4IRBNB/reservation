package com.fourirbnb.reservation.application.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponseInternalDto(UUID id, Long userId, UUID lodgeId, Long price,
                                             LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                             String reservationStatus) implements Serializable {

}
