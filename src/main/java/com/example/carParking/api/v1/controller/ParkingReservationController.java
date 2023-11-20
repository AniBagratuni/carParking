package com.example.carParking.api.v1.controller;

import com.example.carParking.conversion.MapperService;
import com.example.carParking.api.v1.dto.UserParkingReservationDTO;
import com.example.carParking.dao.entity.UserParkingReservation;
import com.example.carParking.exception.NotFoundException;
import com.example.carParking.exception.UserAlreadyBookedException;
import com.example.carParking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-reservations")
public class ParkingReservationController {
    @Autowired
    private ParkingService parkingService;

    @Autowired
    private MapperService mapperService;

    @PostMapping("/book")
    public ResponseEntity<UserParkingReservationDTO> bookParkingSpot(@RequestBody UserParkingReservationDTO userParkingReservationDTO) {
        try {
            var userParkingReservation = mapperService.userParkingReservationDtoToEntity(userParkingReservationDTO);
            UserParkingReservation bookedUserParkingReservation = parkingService.bookParkingSpot(userParkingReservation, userParkingReservationDTO.getResident().getId() );
            return ResponseEntity.ok(mapperService.userParkingReservationEntityToDto(bookedUserParkingReservation));
        } catch (IllegalArgumentException | UserAlreadyBookedException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelBookedParkingSpot(@RequestBody UserParkingReservationDTO userParkingReservationDTO) {
        parkingService.cancelBookedParkingSpot(userParkingReservationDTO.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/park")
    public ResponseEntity<Void> parkParkingSpot(@RequestBody UserParkingReservationDTO userParkingReservationDTO) {
        parkingService.parkParkingSpot(userParkingReservationDTO.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/release")
    public ResponseEntity<Void> releaseParkingSpot(@RequestBody UserParkingReservationDTO userParkingReservationDTO) {
        parkingService.releaseParkingSpot(userParkingReservationDTO.getUserId());
        return ResponseEntity.noContent().build();
    }
}