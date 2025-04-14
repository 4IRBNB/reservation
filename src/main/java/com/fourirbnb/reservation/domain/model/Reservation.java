package com.fourirbnb.reservation.domain.model;

import com.fourirbnb.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@Getter
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE p_reservation SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedFilter")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@Table(name = "p_reservation")
public class Reservation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private UUID lodgeId;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private LocalDateTime checkInDate;

  @Column(nullable = false)
  private LocalDateTime checkOutDate;

  @Column(nullable = false)
  private ReservationStatus reservationStatus;

  public Reservation(Long userId, UUID lodgeId, Long price, LocalDateTime checkInDate,
      LocalDateTime checkOutDate, ReservationStatus reservationStatus) {

    this.userId = userId;
    this.lodgeId = lodgeId;
    this.price = price;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.reservationStatus = reservationStatus;
  }

  public void update(ReservationStatus reservationStatus) {
    this.reservationStatus = reservationStatus;
  }
}
