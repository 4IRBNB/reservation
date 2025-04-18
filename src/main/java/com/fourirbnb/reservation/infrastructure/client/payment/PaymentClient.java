package com.fourirbnb.reservation.infrastructure.client.payment;

import com.fourirbnb.common.FeignInterceptor.NoAuthFeignClient;
import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.infrastructure.client.payment.dto.CreatePaymentRequestDto;
import com.fourirbnb.reservation.infrastructure.client.payment.dto.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment", url = "http://localhost:19096")
@NoAuthFeignClient
public interface PaymentClient {

  @PostMapping("/internal/payments")
  BaseResponse<PaymentResponseDto> createPayment(@RequestBody CreatePaymentRequestDto request);

}
