package com.fourirbnb.reservation.infrastructure.client.notification;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.domain.model.NotificationData;
import com.fourirbnb.reservation.domain.port.NotificationPort;
import com.fourirbnb.reservation.infrastructure.client.notification.dto.CreateNotificationRequestDto;
import com.fourirbnb.reservation.infrastructure.client.notification.dto.NotificationResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPort {

  private final NotificationClient notificationClient;

  @Override
  public BaseResponse<NotificationResponseDto> createNotification(
      UUID reservationId, Long userId, UUID lodgeId,
      LocalDateTime checkInDate, LocalDateTime checkOutDate, String reservationStatus) {

    CreateNotificationRequestDto request = new CreateNotificationRequestDto(
        reservationId, userId, lodgeId, checkInDate, checkOutDate, reservationStatus
    );

    return notificationClient.createNotification(request);
  }

  @Override
  public NotificationData toDomainModel(BaseResponse<NotificationResponseDto> dto) {

    NotificationResponseDto response = dto.getData();

    return new NotificationData(
        response.notificationId(), response.userId(), response.title(),
        response.message(), response.type(), response.isSuccess()
    );
  }
}
