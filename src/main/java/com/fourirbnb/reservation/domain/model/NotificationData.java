package com.fourirbnb.reservation.domain.model;

import java.util.UUID;

public record NotificationData(UUID notificationId, Long userId, String title,
                                      String message, String type, Boolean isSuccess) {

}

