package com.fourirbnb.reservation.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class ReservationServiceTest {

  @Autowired
  private ReservationService reservationService;

  @MockitoBean
  private ReservationRepository reservationRepository;


  @Test
  @DisplayName("예약 생성 테스트")
  void createReservation() {
    LocalDateTime checkInDate = LocalDateTime.of(2025, 4, 12, 15, 0, 0);
    LocalDateTime checkOutDate = LocalDateTime.of(2025, 4, 14, 11, 0, 0);

    CreateReservationInternalDto request = new CreateReservationInternalDto(
        1L, UUID.randomUUID(), 200_000L, checkInDate, checkOutDate
    );

    ReservationResponseInternalDto response = reservationService.createReservation(request);

    log.info("예약 응답 : {}", response.toString());

    assertEquals(1L, response.userId());
  }

}