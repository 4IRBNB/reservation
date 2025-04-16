package com.fourirbnb.reservation.infrastructure.client.payment;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.domain.model.PaymentData;
import com.fourirbnb.reservation.domain.port.PaymentPort;
import com.fourirbnb.reservation.infrastructure.client.payment.dto.CreatePaymentRequestDto;
import com.fourirbnb.reservation.infrastructure.client.payment.dto.PaymentResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentClientAdapter implements PaymentPort {

  private final PaymentClient paymentClient;

  @Override
  public BaseResponse<PaymentResponseDto> createPayment(
      UUID reservationId, Long amount, Boolean couponUsage) {

    CreatePaymentRequestDto request = new CreatePaymentRequestDto(
        reservationId, amount, couponUsage
    );

    return paymentClient.createPayment(request);
  }

  @Override
  public PaymentData toDomainModel(BaseResponse<PaymentResponseDto> dto) {

    PaymentResponseDto data = dto.getData();

    return new PaymentData(
        data.id(), data.reservationId(), data.amount(), data.couponUsage(), data.paymentStatus()
    );
  }
}
