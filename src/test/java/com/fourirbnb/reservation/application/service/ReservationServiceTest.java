package com.fourirbnb.reservation.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fourirbnb.common.exception.ResourceNotFoundException;
import com.fourirbnb.reservation.ReservationApplication;
import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.application.dto.UpdateReservationInternalDto;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = ReservationApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Slf4j
@Transactional
class ReservationServiceTest {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationRepository reservationRepository;

  private final UUID lodgeId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

  private final UUID lodgeId2 = UUID.fromString("987fbc97-4bed-5078-9f07-9141ba07c9f3");

  private UUID reservationId;

  @Autowired
  private ReservationFacade reservationFacade;

  @BeforeEach
  void setUp() {

    entityManager.unwrap(Session.class)
        .enableFilter("deletedFilter");

    reservationRepository.deleteAll();

    LocalDateTime checkInDate = LocalDateTime.of(2025, 1, 1, 15, 0, 0);
    LocalDateTime checkOutDate = LocalDateTime.of(2025, 1, 3, 11, 0, 0);

    CreateReservationInternalDto request = new CreateReservationInternalDto(
        1L, lodgeId1, 300_000L, checkInDate, checkOutDate
    );

    ReservationResponseInternalDto response = reservationService.createReservation(request);

    reservationId = response.id();
  }

  @Test
  @DisplayName("예약 생성 테스트")
  @Order(1)
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

  @Test
  @DisplayName("예약 전체 목록 조회 테스트")
  @Order(2)
  void getReservations() {

    Pageable pageable = PageRequest.of(0, 10);
    Page<ReservationResponseInternalDto> reservations = reservationService
        .getReservations(pageable);

    log.info("Reservation Size : {}", reservations.getContent().size());

    assertEquals(10, reservations.getContent().size());
  }

  @Test
  @DisplayName("나의 예약 목록 조회 테스트")
  @Order(3)
  void getMyReservations() {

    Long userId = 1L;

    Pageable pageable = PageRequest.of(0, 10);

    Page<ReservationResponseInternalDto> reservations = reservationService
        .getMyReservations(userId, pageable);

    log.info("User Id : {}", userId);

    assertEquals(userId, reservations.getContent().get(1).userId());
  }

  @Test
  @DisplayName("숙소의 예약 목록 조회 테스트")
  @Order(4)
  void getLodgeReservations() {

    Pageable pageable = PageRequest.of(0, 10);

    Page<ReservationResponseInternalDto> reservations = reservationService
        .getLodgeReservations(lodgeId1, pageable);

    log.info("Lodge1 Id : {}", lodgeId1);
    log.info("Lodge2 Id : {}", lodgeId2);

    assertEquals(lodgeId1, reservations.getContent().get(1).lodgeId());
    assertNotEquals(lodgeId2, reservations.getContent().get(2).lodgeId());
  }

  @Test
  @DisplayName("예약 단건 조회 테스트")
  @Order(5)
  void getReservationById() {

    log.info("Reservation Id : {}", reservationId);

    ReservationResponseInternalDto findReservation = reservationService
        .getReservationById(reservationId);

    assertEquals(reservationId, findReservation.id());
  }

  @Test
  @DisplayName("예약 상태 변경 테스트")
  @Order(6)
  void updateReservationStatus() {

    UpdateReservationInternalDto request = new UpdateReservationInternalDto(
        "COMPLETED"
    );

    ReservationResponseInternalDto response = reservationService
        .updateReservationStatus(reservationId, request);

    log.info("Updated Reservation Status : {}", response.reservationStatus());

    assertEquals(reservationId, response.id());
    assertEquals(request.reservationStatus(), response.reservationStatus());
  }

  @Test
  @DisplayName("예약 삭제 테스트 : Soft Delete")
  @Order(6)
  void deleteReservation() {

    ReservationResponseInternalDto reservation =
        reservationService.deleteReservation(reservationId);

    entityManager.clear();

    assertThrows(ResourceNotFoundException.class, () -> {
      reservationService.getReservationById(reservationId);
    });
  }

  @Test
  @DisplayName("예약 생성 동시성 제어 테스트 : 비관적 락")
  @Order(7)
  void createReservationWithLock() throws InterruptedException {

    LocalDateTime checkInDate = LocalDateTime.of(2025, 4, 17, 15, 0, 0);
    LocalDateTime checkOutDate = LocalDateTime.of(2025, 4, 20, 11, 0, 0);

    AtomicReference<UUID> reservationId1 = new AtomicReference<>();
    AtomicReference<UUID> reservationId2 = new AtomicReference<>();

    CreateReservationInternalDto request1 = new CreateReservationInternalDto(
        1L, lodgeId1, 400_000L, checkInDate, checkOutDate
    );

    CreateReservationInternalDto request2 = new CreateReservationInternalDto(
        2L, lodgeId1, 400_000L, checkInDate, checkOutDate
    );

    Runnable task1 = () -> {
      ReservationResponseInternalDto response1 = reservationFacade.createReservation(request1);
      reservationId1.set(response1.id());
    };

    Runnable task2 = () -> {
      ReservationResponseInternalDto response2 = reservationFacade.createReservation(request2);
      reservationId2.set(response2.id());
    };

    Thread thread1 = new Thread(task1);

    Thread thread2 = new Thread(task2);

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    assertNotNull(reservationId1.get());
    assertNull(reservationId2.get());
  }
}