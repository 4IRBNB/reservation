package com.fourirbnb.reservation.presentation.controller;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.common.response.Pagination;
import com.fourirbnb.reservation.application.dto.CacheResponseDto;
import com.fourirbnb.reservation.application.dto.CreateReservationInternalDto;
import com.fourirbnb.reservation.application.dto.ReservationResponseInternalDto;
import com.fourirbnb.reservation.application.service.ReservationFacade;
import com.fourirbnb.reservation.application.service.ReservationService;
import com.fourirbnb.reservation.presentation.dto.CreateReservationDto;
import com.fourirbnb.reservation.presentation.dto.ReservationResponseDto;
import com.fourirbnb.reservation.presentation.dto.UpdateReservationDto;
import com.fourirbnb.reservation.presentation.mapper.ReservationDtoMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;
  private final ReservationFacade reservationFacade;

  @PostMapping("")
  public BaseResponse<ReservationResponseDto> createReservation(
      @RequestBody CreateReservationDto request) {

    CreateReservationInternalDto internalDto = ReservationDtoMapper.toCreateInternalDto(request);

    ReservationResponseInternalDto response = reservationFacade.createReservation(internalDto);

    return BaseResponse.SUCCESS(
        ReservationDtoMapper.toResponse(response),
        "예약 생성 성공", HttpStatus.CREATED.value()
    );
  }

  @GetMapping("")
  public BaseResponse<List<ReservationResponseDto>> getReservations(Pageable pageable) {

    Page<ReservationResponseInternalDto> responsePage = reservationService.getReservations(
        pageable);

    Pagination pagination = new Pagination(
        responsePage.getTotalPages(),
        responsePage.getTotalElements(),
        responsePage.getNumber(),
        responsePage.getNumberOfElements()
    );

    return BaseResponse.SUCCESS(
        ReservationDtoMapper.toResponseList(responsePage.getContent()),
        "예약 전체 목록 조회 성공", pagination
    );
  }

  @GetMapping("/me")
  public BaseResponse<List<ReservationResponseDto>> getMyReservations(
      @RequestParam Long userId, Pageable pageable) {

    Page<ReservationResponseInternalDto> responsePage = reservationService
        .getMyReservations(userId, pageable);

    Pagination pagination = new Pagination(
        responsePage.getTotalPages(),
        responsePage.getTotalElements(),
        responsePage.getNumber(),
        responsePage.getNumberOfElements()
    );

    return BaseResponse.SUCCESS(
        ReservationDtoMapper.toResponseList(responsePage.getContent()),
        "나의 예약 목록 조회 성공", pagination
    );
  }

  @GetMapping("/lodge")
  public BaseResponse<List<ReservationResponseDto>> getLodgeReservations(
      @RequestParam UUID lodgeId, Pageable pageable) {

    CacheResponseDto<ReservationResponseInternalDto> reservations =
        reservationService.getLodgeReservations(lodgeId, pageable);

    Pagination pagination = new Pagination(
        reservations.getTotalPages(),
        reservations.getTotalElements(),
        reservations.getPageNumber(),
        reservations.getPageSize()
    );

    return BaseResponse.SUCCESS(
        ReservationDtoMapper.toResponseList(reservations.getContent()),
        "객실 예약 조회 성공", pagination
    );
  }

  @GetMapping("/{reservationId}")
  public BaseResponse<ReservationResponseDto> getReservationById(@PathVariable UUID reservationId) {

    ReservationResponseInternalDto response = reservationService.getReservationById(reservationId);

    return BaseResponse.SUCCESS(
        ReservationDtoMapper.toResponse(response),
        "예약 조회 성공 : " + reservationId, HttpStatus.OK.value()
    );
  }

  @PatchMapping("/{reservationId}")
  public BaseResponse<ReservationResponseDto> updateReservationStatus(
      @PathVariable UUID reservationId, @RequestBody UpdateReservationDto request) {

    ReservationResponseInternalDto response = reservationFacade
        .updateReservationStatus(reservationId, ReservationDtoMapper.toUpdateInternalDto(request));

    return BaseResponse.SUCCESS(
        ReservationDtoMapper.toResponse(response),
        "예약 상태 수정 성공 : " + response.reservationStatus(), HttpStatus.OK.value()
    );
  }

  @DeleteMapping("/{reservationId}")
  public BaseResponse<ReservationResponseDto> deleteReservation(@PathVariable UUID reservationId) {

    ReservationResponseInternalDto response = reservationService.deleteReservation(reservationId);

    return BaseResponse.SUCCESS(
        ReservationDtoMapper.toResponse(response),
        "예약 삭제 성공 : " + reservationId, HttpStatus.OK.value()
    );
  }
}
