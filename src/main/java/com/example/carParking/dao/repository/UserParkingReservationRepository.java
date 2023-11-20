package com.example.carParking.dao.repository;

import com.example.carParking.dao.entity.UserParkingReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserParkingReservationRepository extends JpaRepository<UserParkingReservation, Long> {
    UserParkingReservation findByUserId(Long userId);
}

