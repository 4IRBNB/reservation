package com.fourirbnb.reservation.application.event;

import java.util.UUID;

public record PaymentRequestEvent(UUID reservationId, Long amount, Boolean couponUsage) {

}
