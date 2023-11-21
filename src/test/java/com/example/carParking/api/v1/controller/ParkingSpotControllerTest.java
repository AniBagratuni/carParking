package com.example.carParking.api.v1.controller;

import com.example.carParking.api.v1.dto.ParkingSpotDTO;
import com.example.carParking.api.v1.dto.ResidentDTO;
import com.example.carParking.conversion.MapperService;
import com.example.carParking.dao.entity.ParkingSpot;
import com.example.carParking.dao.entity.Resident;
import com.example.carParking.dao.enums.Status;
import com.example.carParking.service.ParkingSpotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkingSpotController.class)
public class ParkingSpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingSpotService parkingSpotService;

    @MockBean
    private MapperService mapperService;

    @Autowired
    private ObjectMapper objectMapper;

    private ParkingSpotDTO parkingSpotDTO;
    private ParkingSpot parkingSpot;

    @BeforeEach
    void init() {
        parkingSpotDTO = new ParkingSpotDTO()
                .setId(1L)
                .setStatus(Status.FREE)
                .setSpotNumber("1A")
                .setResident(new ResidentDTO(1L,"R1", true, 0.0, 0.0));

        parkingSpot = new ParkingSpot()
                .setId(1L)
                .setStatus(Status.FREE)
                .setSpotNumber("1A")
                .setResident(new Resident(1L,"R1", true, 0.0, 0.0));
    }

    @Test
    void testGetAllParkingSpots() throws Exception {
        when(parkingSpotService.getAllParkingSpots()).thenReturn(Collections.singletonList(parkingSpot));

        mockMvc.perform(get("/api/v1/parking-spots"))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify the service method was called
        verify(parkingSpotService, times(1)).getAllParkingSpots();
    }

    @Test
    void testGetAllAvailableParkingSpots() throws Exception {
        Long residentId = 1L;
        when(parkingSpotService.getAvailableParkingSpots(residentId)).thenReturn(Collections.singletonList(parkingSpot));

        mockMvc.perform(get("/api/v1/parking-spots/{residentId}", residentId))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify the service method was called
        verify(parkingSpotService, times(1)).getAvailableParkingSpots(residentId);
    }

    @Test
    void testCreateParkingSpot() throws Exception {

        when(mapperService.parkingSpotDtoToEntity(any(ParkingSpotDTO.class))).thenReturn(parkingSpot);

        when(parkingSpotService.createParkingSpot(any(ParkingSpot.class))).thenReturn(parkingSpot);

        String json = objectMapper.writeValueAsString(parkingSpotDTO);

        mockMvc.perform(post("/api/v1/parking-spots")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        // Verify the service method was called
        verify(parkingSpotService, times(1)).createParkingSpot(any(ParkingSpot.class));
    }

    @Test
    void testDeleteParkingSpot() throws Exception {
        doNothing().when(parkingSpotService).deleteParkingSpot(any(Long.class));

        mockMvc.perform(delete("/api/v1/parking-spots/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        // Verify the service method was called
        verify(parkingSpotService, times(1)).deleteParkingSpot(any(Long.class));
    }
}
