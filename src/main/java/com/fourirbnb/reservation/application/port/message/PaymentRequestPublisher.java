package com.fourirbnb.reservation.application.port.message;

import com.fourirbnb.reservation.application.event.PaymentRequestEvent;

public interface PaymentRequestPublisher {

  void publish(PaymentRequestEvent event);

}
