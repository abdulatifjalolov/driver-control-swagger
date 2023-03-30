package com.example.swagger.controller;

import com.example.swagger.entity.CarEntity;
import com.example.swagger.entity.model.dto.CarRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.lifecycle.Startables;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarControllerTest extends BaseTest {
    private final String PATH_CAR = "/api/car";
    @BeforeAll
    static void beforeAll() {
        Startables.deepStart(postgres).join();
    }

    @AfterEach
    void afterAll() {
        carRepository.deleteAll();
    }

    @Test
    @WithMockUser(authorities = "READ")
    @DisplayName("shouldGetCarOkStatus")
    void getCar() throws Exception {
        CarEntity carEntity = addMockCar();
        callGet(carEntity.getId()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "READ")
    @DisplayName("shouldGetCarThrowNotFound")
    void getCarThrow() throws Exception {
        callGet(3L).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldAddCarCreateStatus")
    void addCar() throws Exception {
        callAdd().andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldAddCarConflictStatus")
    void addCarThrow() throws Exception {
        callAdd();
        callAdd().andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldDeleteCarOkStatus")
    void deleteCar() throws Exception {
        CarEntity carEntity = addMockCar();
        callDelete(carEntity.getId()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldUpdateCarCreatedStatus")
    void updateCar() throws Exception {
        CarEntity mac = addMockCar();
        callUpdate(mac.getId()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldUpdateCarNotFoundStatus")
    void updateCarThrow() throws Exception {
        callUpdate(3L).andExpect(status().isNotFound());
    }
    private ResultActions callUpdate(Long id) throws Exception {
        CarRequestDTO carRequestDTO = new CarRequestDTO(
                "mac",
                "apple",
                "12345678",
                2023,
                700.0
        );

        final MockHttpServletRequestBuilder request =
                put(PATH_CAR + "/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequestDTO));
        return mockMvc.perform(request);
    }
    private ResultActions callDelete(Long id) throws Exception {
        final MockHttpServletRequestBuilder request =
                delete(PATH_CAR + "/{id}", id);
        return mockMvc.perform(request);
    }

    private ResultActions callAdd() throws Exception {
        CarRequestDTO carRequestDTO = new CarRequestDTO(
                "mac",
                "apple",
                "12345678",
                2023,
                700.0
        );

        final MockHttpServletRequestBuilder request =
                post(PATH_CAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequestDTO));
        return mockMvc.perform(request);
    }

    private ResultActions callGet(Long id) throws Exception {
        final MockHttpServletRequestBuilder request =
                get(PATH_CAR + "/{id}", id);
        return mockMvc.perform(request);
    }

    private CarEntity addMockCar() {
        CarEntity carEntity = new CarEntity();
        carEntity.setName("mac");
        carEntity.setMileage(700.0);
        carEntity.setModel("apple");
        carEntity.setStateNumber("12345678");
        carEntity.setModelYear(2023);
        return carRepository.save(carEntity);
    }

}
