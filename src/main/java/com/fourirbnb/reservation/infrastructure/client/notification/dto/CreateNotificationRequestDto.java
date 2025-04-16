package com.fourirbnb.reservation.infrastructure.client.notification.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateNotificationRequestDto(UUID reservationId, Long userId, UUID lodgeId,
                                           LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                           String reservationStatus) {

}
