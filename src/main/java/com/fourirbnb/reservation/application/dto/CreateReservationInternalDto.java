package com.fourirbnb.reservation.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationInternalDto(Long userId, UUID lodgeId, Long price,
                                           LocalDateTime checkInDate, LocalDateTime checkOutDate) {

}
