package com.fourirbnb.reservation.application.port.message;

import java.util.UUID;

public interface PaymentCancelRequestPublisher {

  void publish(UUID reservationId);
}
