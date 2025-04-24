package com.fourirbnb.reservation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ReservationResponseDto.class, name = "ReservationResponseDTO")
})
public record ReservationResponseDto(UUID id, Long userId, UUID lodeId, Long price,
                                     LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                     String reservationStatus) implements Serializable {

}
