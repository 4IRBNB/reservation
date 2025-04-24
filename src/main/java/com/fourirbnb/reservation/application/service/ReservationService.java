package com.fourirbnb.reservation.application.service;

import com.fourirbnb.common.exception.ResourceNotFoundException;
import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.application.dto.UpdateReservationInternalDto;
import com.fourirbnb.reservation.application.mapper.ReservationMapper;
import com.fourirbnb.reservation.domain.model.Reservation;
import com.fourirbnb.reservation.domain.model.ReservationStatus;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import com.fourirbnb.reservation.domain.service.ReservationDomainService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationDomainService reservationDomainService;
  private final PostgresLockExecutor postgresLockExecutor;

  @Transactional
  @CacheEvict(value = "lodgeReservations", allEntries = true)
  public ReservationResponseInternalDto createReservation(
      CreateReservationInternalDto internalDto) {

    postgresLockExecutor.execute(
        internalDto.lodgeId(), internalDto.checkInDate(), internalDto.checkOutDate()
    );

    Reservation reservation = ReservationMapper.toEntity(internalDto);

    reservation.pending();

    reservationDomainService.validateLodgeAvailable(reservation);

    reservationRepository.save(reservation);

    return ReservationMapper.toResponse(reservation);
  }

  @Transactional(readOnly = true)
  public Page<ReservationResponseInternalDto> getReservations(Pageable pageable) {

    Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

    if (!reservationPage.hasContent()) {
      throw new ResourceNotFoundException("예약 조회 실패 : 예약이 존재하지 않음");
    }

    return ReservationMapper.toResponsePage(reservationPage);
  }

  @Transactional(readOnly = true)
  public Page<ReservationResponseInternalDto> getMyReservations(Long userId, Pageable pageable) {

    Page<Reservation> reservationPage = reservationRepository.findAllByUserId(userId, pageable);

    if (!reservationPage.hasContent()) {
      throw new ResourceNotFoundException("예약 조회 실패 : 예약이 존재하지 않음");
    }

    return ReservationMapper.toResponsePage(reservationPage);
  }

  @Transactional(readOnly = true)
  @Cacheable(
      cacheNames = "reservationCache",
      key = "'lodge:'+#lodgeId.toString()"
  )
  public Page<ReservationResponseInternalDto> getLodgeReservations(UUID lodgeId,
      Pageable pageable) {

    Page<Reservation> reservationPage = reservationRepository.findAllByLodgeId(lodgeId, pageable);

    if (!reservationPage.hasContent()) {
      throw new ResourceNotFoundException("예약 조회 실패 : 예약이 존재하지 않음");
    }

    return ReservationMapper.toResponsePage(reservationPage);
  }

  @Transactional(readOnly = true)
  public ReservationResponseInternalDto getReservationById(UUID reservationId) {

    Reservation reservation = findReservationById(reservationId);

    return ReservationMapper.toResponse(reservation);
  }

  @Transactional
  @CacheEvict(value = "lodgeReservations", allEntries = true)
  public ReservationResponseInternalDto updateReservationStatus(
      UUID reservationId, UpdateReservationInternalDto request) {

    Reservation reservation = findReservationById(reservationId);

    reservation.update(ReservationStatus.valueOf(request.reservationStatus()));

    reservationRepository.save(reservation);

    return ReservationMapper.toResponse(reservation);
  }

  @Transactional
  @CacheEvict(value = "lodgeReservations", allEntries = true)
  public ReservationResponseInternalDto deleteReservation(UUID reservationId) {

    Reservation reservation = findReservationById(reservationId);

    reservation.delete(1L);

    reservationRepository.save(reservation);

    return ReservationMapper.toResponse(reservation);
  }

  private Reservation findReservationById(UUID reservationId) {

    return reservationRepository.findById(reservationId)
        .orElseThrow(() -> new ResourceNotFoundException("예약 조회 실패 : 예약이 존재하지 않음"));
  }

  @Transactional
  public void updateToReserve(UUID reservationId) {
    Reservation reservation = findReservationById(reservationId);
    reservation.reserve();
  }
}
