package com.fourirbnb.reservation.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationDto(Long userId, UUID lodgeId, Long price,
                                   LocalDateTime checkInDate, LocalDateTime checkOutDate) {

}
