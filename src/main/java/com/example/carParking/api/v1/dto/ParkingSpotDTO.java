package com.example.carParking.api.v1.dto;

import com.example.carParking.dao.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ParkingSpotDTO {
    private Long id;
    private String spotNumber;
    private Status status = Status.FREE;
    private ResidentDTO resident;
}
