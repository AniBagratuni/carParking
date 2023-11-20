package com.example.carParking.api.v1.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class UserParkingReservationDTO {
    private String spotNumber;
    private ResidentDTO resident;
    private Long userId;
    private LocalDateTime bookStartTime;
    private LocalDateTime endTime;
}
