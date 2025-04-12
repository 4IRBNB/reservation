package com.fourirbnb.reservation.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponseDto(UUID id, Long userId, UUID lodeId, Long price,
                                     LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                     String reservationStatus) {

}
