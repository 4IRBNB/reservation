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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  private final ReservationDomainService reservationDomainService;

  @Transactional
  public ReservationResponseInternalDto createReservation(
      CreateReservationInternalDto internalDto) {

    Reservation reservation = ReservationMapper.toEntity(internalDto);

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

    Reservation reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new ResourceNotFoundException("예약 조회 실패 : 예약이 존재하지 않음"));

    return ReservationMapper.toResponse(reservation);
  }

  @Transactional
  public ReservationResponseInternalDto updateReservationStatus(
      UUID reservationId, UpdateReservationInternalDto request) {

    Reservation reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new ResourceNotFoundException("예약 수정 실패 : 예약이 존재하지 않음"));

    reservation.update(ReservationStatus.valueOf(request.reservationStatus()));

    reservationRepository.save(reservation);

    return ReservationMapper.toResponse(reservation);
  }

  @Transactional
  public ReservationResponseInternalDto deleteReservation(UUID reservationId) {

    Reservation reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new ResourceNotFoundException("예약 삭제 실패 : 예약이 존재하지 않음"));

    reservationRepository.deleteById(reservationId);

    return ReservationMapper.toResponse(reservation);
  }
}
