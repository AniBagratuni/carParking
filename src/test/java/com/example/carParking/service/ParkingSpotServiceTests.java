package com.example.carParking.service;

import com.example.carParking.dao.entity.ParkingSpot;
import com.example.carParking.dao.enums.Status;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParkingSpotServiceTests {
    @Autowired
    private ParkingSpotService parkingSpotService;

    @Test
    @Order(1)
    void testGetAvailableParkingSpots() {
        // Test the service method
        List<ParkingSpot> availableSpots = parkingSpotService.getAvailableParkingSpots(1L);

        // Verify the result
        assertEquals(2, availableSpots.size());
    }

    @Test
    @Order(2)
    void testUpdateParkingSpot() {
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(1L);
        parkingSpot.setStatus(Status.BOOKED);

        // Test the service method
        ParkingSpot updatedParkingSpot = parkingSpotService.updateParkingSpot(parkingSpot);

        // Verify the result
        assertEquals(Status.BOOKED, updatedParkingSpot.getStatus());
    }
}
