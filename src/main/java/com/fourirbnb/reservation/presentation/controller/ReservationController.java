package com.fourirbnb.reservation.presentation.controller;

import com.fourirbnb.reservation.application.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;

}
