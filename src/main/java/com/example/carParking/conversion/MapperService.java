package com.example.carParking.conversion;

import com.example.carParking.api.v1.dto.ParkingSpotDTO;
import com.example.carParking.dao.entity.ParkingSpot;
import com.example.carParking.dao.entity.UserParkingReservation;
import com.example.carParking.api.v1.dto.UserParkingReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface MapperService {

    @Mapping(target = "parkingSpot", ignore = true )
    UserParkingReservation userParkingReservationDtoToEntity(UserParkingReservationDTO userParkingReservationDTO);

    @Mapping(target = "spotNumber", source = "parkingReservation.parkingSpot.spotNumber" )
    @Mapping(target = "resident", source = "parkingReservation.parkingSpot.resident" )
    UserParkingReservationDTO userParkingReservationEntityToDto(UserParkingReservation parkingReservation);

    ParkingSpot parkingSpotDtoToEntity(ParkingSpotDTO parkingSpotDTO);

    ParkingSpotDTO parkingSpotEntityToDto(ParkingSpot parkingSpot);
}
