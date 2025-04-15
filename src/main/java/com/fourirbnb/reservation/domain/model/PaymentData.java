package com.fourirbnb.reservation.domain.model;

import java.util.UUID;

public record PaymentData(UUID id, UUID reservationId, Long amount,
                          Boolean couponUsage, String paymentStatus) {

}
