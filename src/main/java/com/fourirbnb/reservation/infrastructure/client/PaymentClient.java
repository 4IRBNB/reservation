package com.fourirbnb.reservation.infrastructure.client;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.reservation.infrastructure.client.dto.CreatePaymentRequestDto;
import com.fourirbnb.reservation.infrastructure.client.dto.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment", url = "http://localhost:19096")
public interface PaymentClient {

  @PostMapping("/internal/payments")
  BaseResponse<PaymentResponseDto> createPayment(@RequestBody CreatePaymentRequestDto request);

}
