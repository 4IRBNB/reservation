package com.fourirbnb.reservation.application.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostgresLockExecutor implements LockExecutor {

  @PersistenceContext
  private EntityManager em;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void execute(UUID lodgeId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
    boolean locked = tryLock(lodgeId, checkInDate, checkOutDate);
    if (!locked) {
      throw new IllegalStateException("이미 동일한 날짜에 대한 예약이 진행 중입니다.");
    }
  }

  private long generateKey(UUID lodgeId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
    String compositeKey = lodgeId.toString() + checkInDate.toString() + checkOutDate.toString();
    log.info("Generated composite key: {}", compositeKey);
    return compositeKey.hashCode();
  }

  private boolean tryLock(UUID lodgeId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
    long lockKey = generateKey(lodgeId, checkInDate, checkOutDate);

    Boolean locked = (Boolean) entityManager
        .createNativeQuery("SELECT pg_try_advisory_xact_lock(?)")
        .setParameter(1, lockKey)
        .getSingleResult();

    return Boolean.TRUE.equals(locked);
  }
}
