package com.example.carParking.service;

import com.example.carParking.api.v1.dto.ResidentDTO;
import com.example.carParking.conversion.MapperService;
import com.example.carParking.dao.entity.UserParkingReservation;
import com.example.carParking.dao.enums.Status;
import com.example.carParking.dao.repository.UserParkingReservationRepository;
import com.example.carParking.api.v1.dto.UserParkingReservationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("integration")
public class ParkingServiceTests {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private UserParkingReservationRepository userParkingReservationRepository;

    @Test
    void testBookParkingSpot() {
        Long residentId = 1L;
        Long spotId = 1L;
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = startTime.plusHours(5);

        ResidentDTO resident = new ResidentDTO().setId(residentId).setName("resident1");

        UserParkingReservationDTO userParkingReservationDTO = new UserParkingReservationDTO()
                .setResident(resident)
                .setUserId(1L)
                .setBookStartTime(startTime)
                .setEndTime(endTime);


        var userParkingReservation = mapperService.userParkingReservationDtoToEntity(userParkingReservationDTO);
        // Test the service method
        UserParkingReservation bookedParkingReservation = parkingService.bookParkingSpot(userParkingReservation, residentId);

        assertEquals(2, parkingService.getUserParkingReservation().size());

        // Verify the result
        assertNotNull(bookedParkingReservation);
        assertEquals(spotId, bookedParkingReservation.getParkingSpot().getId());
        assertEquals(Status.BOOKED, bookedParkingReservation.getParkingSpot().getStatus());
        assertEquals(residentId, bookedParkingReservation.getParkingSpot().getResident().getId());
    }

    @Test
    void testParkParkingSpot() {
        long userId = 2L;
        UserParkingReservation parkedParkingReservation = parkingService.parkParkingSpot(userId);

        assertNotNull(parkedParkingReservation);
        assertEquals(Status.OCCUPIED, parkedParkingReservation.getParkingSpot().getStatus());
    }

    @Test
    void testReleaseParkingSpot() {
        long userId = 2L;
        parkingService.releaseParkingSpot(userId);

        assertEquals(1, parkingService.getUserParkingReservation().size());
    }
}
