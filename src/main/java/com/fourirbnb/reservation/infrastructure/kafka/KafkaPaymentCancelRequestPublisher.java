package com.fourirbnb.reservation.infrastructure.kafka;

import com.fourirbnb.reservation.application.port.message.PaymentCancelRequestPublisher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPaymentCancelRequestPublisher implements PaymentCancelRequestPublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void publish(UUID reservationId) {
    kafkaTemplate.send("payment-cancel-request", reservationId.toString());
    
  }
}
