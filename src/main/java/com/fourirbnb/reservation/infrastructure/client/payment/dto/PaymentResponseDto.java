package com.fourirbnb.reservation.infrastructure.client.payment.dto;

import java.util.UUID;

public record PaymentResponseDto(UUID id, UUID reservationId, Long amount,
                                 Boolean couponUsage, String paymentStatus) {

}