package com.fourirbnb.reservation.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourirbnb.common.exception.InternalServerException;
import com.fourirbnb.reservation.application.event.PaymentResponseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentResponseListener {

  private final ReservationMessageService reservationMessageService;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "payment-response-topic", groupId = "reservation-group")
  public void listen(String message) {
    try {
      PaymentResponseEvent event = objectMapper.readValue(message, PaymentResponseEvent.class);
      reservationMessageService.updateStatusFromPayment(event);
    } catch (Exception e) {
      log.info("결제 실패 : {}", e.getMessage());
      throw new InternalServerException(e.getMessage());
    }
  }
}
