package com.fourirbnb.reservation.domain.port;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.domain.model.NotificationData;
import com.fourirbnb.reservation.infrastructure.client.notification.dto.NotificationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;

public interface NotificationPort {

  BaseResponse<NotificationResponseDto> createNotification(
      UUID reservationId, Long userId, UUID lodgeId,
      LocalDateTime checkInDate, LocalDateTime checkOutDate, String reservationStatus
  );

  NotificationData toDomainModel(BaseResponse<NotificationResponseDto> dto);
}
