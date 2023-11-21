package com.example.carParking.service;

import com.example.carParking.dao.entity.Resident;
import com.example.carParking.dao.entity.UserParkingReservation;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    public void payForParking(UserParkingReservation userParkingReservation) {

        Resident resident = userParkingReservation.getParkingSpot().getResident();

        if (resident.getIsFree()) {
            return;
        }
        LocalDateTime startDate = userParkingReservation.getBookStartTime();
        LocalDateTime endDate = LocalDateTime.now();

        long parkingDuration = calculateParkingDuration(startDate, endDate);

         if (parkingDuration < 30) { // 30m
             // pay min price
             payForParking(resident.getMinPrice());
        } else {
             double price = resident.getPricePerHour();
             double durationPerHours = parkingDuration/60;
             payForParking(price * durationPerHours);
         }
    }

    private long calculateParkingDuration(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        // Get the total minutes from the duration
        return duration.toMinutes();
    }

    private void payForParking(double price) {
    //  do payment
    }
}
