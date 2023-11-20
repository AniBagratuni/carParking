package com.example.carParking.service;

import com.example.carParking.exception.NotFoundException;
import com.example.carParking.exception.SpotNotBookedException;
import com.example.carParking.exception.UserAlreadyBookedException;
import com.example.carParking.dao.entity.ParkingSpot;
import com.example.carParking.dao.entity.UserParkingReservation;
import com.example.carParking.dao.enums.Status;
import com.example.carParking.dao.repository.UserParkingReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class ParkingService {

    @Autowired
    ParkingSpotService parkingSpotService;

    @Autowired
    private UserParkingReservationRepository userParkingReservationRepository;

    public UserParkingReservation bookParkingSpot(UserParkingReservation userParkingReservation, Long residentId) {

        LocalDateTime startTime = userParkingReservation.getBookStartTime();
        if (startTime != null && LocalDateTime.now().isAfter(startTime)) {
            throw new IllegalArgumentException("Start time must be before current time");
        } else {
            startTime = LocalDateTime.now();
        }

        if (userParkingReservation.getEndTime() != null) {
            validateTimeRange(startTime, userParkingReservation.getEndTime());
        }

        Long userId = userParkingReservation.getUserId();
        validateUserExistence(userId);

        List<ParkingSpot> availableParkingSpots = parkingSpotService.getAvailableParkingSpots(residentId);

        if (availableParkingSpots.isEmpty()) {
            log.info("No available Parking Spots for {} residentId .", residentId);
            throw new NotFoundException(String.format("No available Parking Spots for %d residentId.", residentId));
        } else {
            ParkingSpot randomParkingSpot = availableParkingSpots.get(0);
            randomParkingSpot.setStatus(Status.BOOKED);
            UserParkingReservation newuserParkingReservation = new UserParkingReservation()
                    .setParkingSpot(randomParkingSpot)
                    .setBookStartTime(startTime)
                    .setEndTime(userParkingReservation.getEndTime())
                    .setUserId(userId);
            newuserParkingReservation = userParkingReservationRepository.save(newuserParkingReservation);
            parkingSpotService.updateParkingSpot(randomParkingSpot);
            return newuserParkingReservation;
        }
    }

    public UserParkingReservation parkParkingSpot(Long userId) {
        UserParkingReservation userParkingReservation = userParkingReservationRepository.findByUserId(userId);
        if (userParkingReservation != null && Status.BOOKED.equals(userParkingReservation.getParkingSpot().getStatus())) {
            ParkingSpot parkingSpot = userParkingReservation.getParkingSpot();
            parkingSpot.setStatus(Status.OCCUPIED);
            parkingSpotService.updateParkingSpot(parkingSpot);
            return userParkingReservation;
        } else {
            throw new SpotNotBookedException("Parking spot is not booked.");
        }
    }

    public void cancelBookedParkingSpot(Long userId) {
        UserParkingReservation userParkingReservation = userParkingReservationRepository.findByUserId(userId);
        if (userParkingReservation != null && Status.BOOKED.equals(userParkingReservation.getParkingSpot().getStatus())) {
            ParkingSpot parkingSpot = userParkingReservation.getParkingSpot();
            parkingSpot.setStatus(Status.FREE);
            parkingSpotService.updateParkingSpot(parkingSpot);
            userParkingReservationRepository.delete(userParkingReservation);
        } else {
            throw new SpotNotBookedException("Parking spot is not booked.");
        }
    }

    public void releaseParkingSpot(Long userId) {
        UserParkingReservation userParkingReservation = userParkingReservationRepository.findByUserId(userId);
        if (userParkingReservation != null) {
            ParkingSpot parkingSpot = userParkingReservation.getParkingSpot();
            parkingSpot.setStatus(Status.FREE);
            parkingSpotService.updateParkingSpot(parkingSpot);
            userParkingReservationRepository.delete(userParkingReservation);
        } else {
            throw new SpotNotBookedException("Parking spot is not booked or occupied.");
        }
    }

    public List<UserParkingReservation> getUserParkingReservation() {
        return userParkingReservationRepository.findAll();
    }

    private void validateUserExistence(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId is null");
        }
        if (userParkingReservationRepository.findByUserId(userId) != null) {
            log.error("{0} user already has occupied or booked the spot.", userId);
            throw new UserAlreadyBookedException(String.format("%d user already has occupied or booked the spot.", userId));
        }
    }

    private void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }
}
