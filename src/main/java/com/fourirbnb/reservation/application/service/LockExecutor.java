package com.fourirbnb.reservation.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LockExecutor {

  void execute(UUID lodgeId, LocalDateTime checkInDate, LocalDateTime checkOutDate);
}
