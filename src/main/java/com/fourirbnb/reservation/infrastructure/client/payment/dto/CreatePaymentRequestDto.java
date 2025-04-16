package com.fourirbnb.reservation.infrastructure.client.payment.dto;

import java.util.UUID;

public record CreatePaymentRequestDto(UUID reservationId, Long amount, Boolean couponUsage) {

}
