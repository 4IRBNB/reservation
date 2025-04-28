package com.fourirbnb.reservation.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourirbnb.common.exception.InternalServerException;
import com.fourirbnb.reservation.application.event.PaymentRequestEvent;
import com.fourirbnb.reservation.application.port.message.PaymentRequestPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class kafkaPaymentRequestPublisher implements PaymentRequestPublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(PaymentRequestEvent event) {

    try {
      String json = objectMapper.writeValueAsString(event);
      kafkaTemplate.send("payment-request-topic", json);
      log.info("Published payment completed : {}", event.reservationId());
    } catch (JsonProcessingException e) {
      log.info("결제 요청 실패 : {}", e.getMessage());
      throw new InternalServerException(e.getMessage());
    }
  }
}
