package com.example.swagger.controller;

import com.example.swagger.entity.CarEntity;
import com.example.swagger.entity.model.dto.TripHistoryItemDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.lifecycle.Startables;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TripHistoryControllerTest extends BaseTest {

    private final String PATH_TRIP = "/api/trip";

    @BeforeAll
    static void beforeAll() {
        Startables.deepStart(postgres).join();
    }

    @AfterEach
    void afterAll() {
        carRepository.deleteAll();
        tripHistoryItemRepository.deleteAll();
    }


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldAddTripCreateStatus")
    void addTrip() throws Exception {
        callAdd().andExpect(status().isCreated());
    }

    private ResultActions callAdd() throws Exception {
        CarEntity carEntity = carRepository.save(new CarEntity(
                "x7",
                "bmw",
                "12345678",
                2023,
                700.0,
                null
        ));
        TripHistoryItemDTO tripHistoryItemDTO = new TripHistoryItemDTO(
              carEntity.getId(),
              Timestamp.valueOf(LocalDateTime.now()),
              "street1",
              Timestamp.valueOf(LocalDateTime.now()),
                "street2"
        );

        final MockHttpServletRequestBuilder request =
                post(PATH_TRIP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripHistoryItemDTO));
        return mockMvc.perform(request);
    }
}
