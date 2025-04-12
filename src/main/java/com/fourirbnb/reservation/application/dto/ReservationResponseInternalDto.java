package com.fourirbnb.reservation.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponseInternalDto(UUID id, Long userId, UUID lodeId, Long price,
                                             LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                             String reservationStatus) {

}
