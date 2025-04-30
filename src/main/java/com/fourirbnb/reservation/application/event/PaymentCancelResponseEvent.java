package com.fourirbnb.reservation.application.event;

import java.util.UUID;

public record PaymentCancelResponseEvent(UUID reservationId, Boolean cancelled) {

}
