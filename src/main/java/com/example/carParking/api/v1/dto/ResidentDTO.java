package com.example.carParking.api.v1.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResidentDTO {
    private Long id;
    private String name;
    private Boolean isFree;
    private double pricePerHour;
    private double minPrice;
}
