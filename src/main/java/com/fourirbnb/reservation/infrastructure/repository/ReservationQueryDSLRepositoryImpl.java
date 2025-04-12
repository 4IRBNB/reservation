package com.fourirbnb.reservation.infrastructure.repository;

import com.fourirbnb.reservation.domain.model.QReservation;
import com.fourirbnb.reservation.domain.repository.ReservationQueryDSLRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationQueryDSLRepositoryImpl implements ReservationQueryDSLRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public boolean existsByLodgeIdAndPeriodOverlap(UUID lodgeId, LocalDateTime checkInDate,
      LocalDateTime checkOutDate) {

    QReservation reservation = QReservation.reservation;

    return queryFactory.selectOne()
        .from(reservation)
        .where(
            reservation.lodgeId.eq(lodgeId),
            reservation.checkInDate.lt(checkOutDate),
            reservation.checkOutDate.gt(checkInDate)
        )
        .fetchFirst() != null;
  }
}
