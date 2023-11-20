package com.example.carParking.dao.repository;

import com.example.carParking.dao.entity.ParkingSpot;
import com.example.carParking.dao.entity.Resident;
import com.example.carParking.dao.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    ParkingSpot findBySpotNumber(String spotNumber);

    List<ParkingSpot> findByResidentAndStatus(Resident resident, Status status);
}
