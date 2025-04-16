package com.fourirbnb.reservation.infrastructure.client.notification;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.infrastructure.client.notification.dto.CreateNotificationRequestDto;
import com.fourirbnb.reservation.infrastructure.client.notification.dto.NotificationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification", url = "http://localhost:19098")
public interface NotificationClient {

  @PostMapping("/internal/notifications")

  public BaseResponse<NotificationResponseDto> createNotification(
      @RequestBody CreateNotificationRequestDto request);
}
