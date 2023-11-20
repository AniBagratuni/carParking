package com.example.carParking.api.v1.controller;

import com.example.carParking.api.v1.dto.ParkingSpotDTO;
import com.example.carParking.conversion.MapperService;
import com.example.carParking.dao.entity.ParkingSpot;
import com.example.carParking.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/parking-spots")
public class ParkingSpotController {
    @Autowired
    private ParkingSpotService parkingSpotService;

    @Autowired
    private MapperService mapperService;

    @GetMapping
    public ResponseEntity<List<ParkingSpotDTO>> getAllParkingSpots() {
        List<ParkingSpotDTO> parkingSpotDTOList = parkingSpotService.getAllParkingSpots().stream()
                .map(mapperService::parkingSpotEntityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(parkingSpotDTOList);
    }

    @GetMapping("/{residentId}")
    public ResponseEntity<List<ParkingSpotDTO>> getAvailableParkingSpotsByResident(@PathVariable Long residentId) {
        List<ParkingSpotDTO> parkingSpotDTOList = parkingSpotService.getAvailableParkingSpots(residentId).stream()
                .map(mapperService::parkingSpotEntityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(parkingSpotDTOList);
    }

    @PostMapping
    public ResponseEntity<ParkingSpotDTO> createParkingSpot(@RequestBody ParkingSpotDTO parkingSpotDTO) {
        ParkingSpot parkingSpot = mapperService.parkingSpotDtoToEntity(parkingSpotDTO);
        parkingSpot = parkingSpotService.createParkingSpot(parkingSpot);
        ParkingSpotDTO createdParkingSpotDTO = mapperService.parkingSpotEntityToDto(parkingSpot);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParkingSpotDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteParkingSpot(@PathVariable Long id) {
        parkingSpotService.deleteParkingSpot(id);
        return ResponseEntity.noContent().build();
    }
}
