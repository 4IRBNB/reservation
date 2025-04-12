package com.fourirbnb.reservation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {

  RESERVED("RESERVED"),
  CANCELLED("CANCELLED"),
  COMPLETED("COMPLETED");

  private final String description;
}
