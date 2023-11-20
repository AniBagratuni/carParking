package com.example.carParking.service;

import com.example.carParking.exception.NotFoundException;
import com.example.carParking.dao.entity.Resident;
import com.example.carParking.dao.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository residentRepository;

    public Resident getResidentById(Long id) {
        return residentRepository.findById(id).orElseThrow(() -> new NotFoundException("Resident not found for " + id));
    }
}
