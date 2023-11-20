package com.example.carParking.service;

import com.example.carParking.exception.NotFoundException;
import com.example.carParking.dao.entity.ParkingSpot;
import com.example.carParking.dao.entity.Resident;
import com.example.carParking.dao.enums.Status;
import com.example.carParking.dao.repository.ParkingSpotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParkingSpotService {
    @Autowired
    private ParkingSpotRepository spotRepository;

    @Autowired
    ResidentService residentService;

    public List<ParkingSpot> getAllParkingSpots() {
        return spotRepository.findAll();
    }

    public List<ParkingSpot> getAvailableParkingSpots(Long residentId) {
        Resident resident = residentService.getResidentById(residentId);
        return spotRepository.findByResidentAndStatus(resident, Status.FREE);
    }

    public ParkingSpot getParkingSpotById(Long id) {
        return spotRepository.findById(id).orElseThrow(() -> new NotFoundException("Parking spot not found for " + id));
    }

    public ParkingSpot createParkingSpot(ParkingSpot parkingSpot) {
        return spotRepository.save(parkingSpot);
    }

    public void deleteParkingSpot(Long id) {
        spotRepository.deleteById(id);
    }

    public ParkingSpot updateParkingSpot(ParkingSpot parkingSpot) {
        return spotRepository.save(parkingSpot);
    }
}
