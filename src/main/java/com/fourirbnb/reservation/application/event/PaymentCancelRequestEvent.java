package com.fourirbnb.reservation.application.event;

import java.util.UUID;

public record PaymentCancelRequestEvent(UUID reservationId, Boolean cancelled) {

}
