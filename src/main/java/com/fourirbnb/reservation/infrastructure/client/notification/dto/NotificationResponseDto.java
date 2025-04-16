package com.fourirbnb.reservation.infrastructure.client.notification.dto;

import java.util.UUID;

public record NotificationResponseDto(UUID notificationId, Long userId, String title,
                                      String message, String type, Boolean isSuccess) {

}
