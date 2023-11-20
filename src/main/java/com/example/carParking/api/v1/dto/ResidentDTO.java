package com.example.carParking.api.v1.dto;

import com.example.carParking.dao.enums.Status;
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
}
