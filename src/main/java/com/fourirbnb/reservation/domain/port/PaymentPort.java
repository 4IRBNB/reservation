package com.fourirbnb.reservation.domain.port;


import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.domain.model.PaymentData;
import com.fourirbnb.reservation.infrastructure.client.dto.PaymentResponseDto;
import java.util.UUID;

public interface PaymentPort {

  BaseResponse<PaymentResponseDto> createPayment(
      UUID reservationId, Long amount, Boolean couponUsage);

  PaymentData toDomainModel(BaseResponse<PaymentResponseDto> dto);
}
