package com.fourirbnb.reservation.application.service;

import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.application.mapper.ReservationMapper;
import com.fourirbnb.reservation.domain.model.Reservation;
import com.fourirbnb.reservation.domain.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  @Transactional
  public ReservationResponseInternalDto createReservation(
      CreateReservationInternalDto internalDto) {

    Reservation reservation = ReservationMapper.toEntity(internalDto);

    reservationRepository.save(reservation);

    return ReservationMapper.toResponse(reservation);
  }
}
