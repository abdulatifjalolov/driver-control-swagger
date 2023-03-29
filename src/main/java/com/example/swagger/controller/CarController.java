package com.example.swagger.controller;

import com.example.swagger.config.swagger.SwaggerConfig;
import com.example.swagger.controller.response.ApiResponse;
import com.example.swagger.entity.model.dto.request.CarRequestDTO;
import com.example.swagger.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/car")
@SecurityRequirement(name = SwaggerConfig.BEARER)
public class CarController {
    private final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registration new car")
    public ApiResponse<Void> create(
            @Valid @RequestBody CarRequestDTO carRequestDTO
    ) {
        carService.create(carRequestDTO);
        return new ApiResponse<>();
    }

    @GetMapping("/{id}")
    @Operation(summary = "To get car")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> get(
            @PathVariable Long id
    ) {
        return new ApiResponse<>(
                carService.get(id)
        );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "To update car")
    public ApiResponse<?> update(
            @PathVariable Long id,
            @Valid @RequestBody CarRequestDTO carRequestDTO
    ) {
        return new ApiResponse<>(
                carService.update(id, carRequestDTO)
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "To delete car")
    public ApiResponse<Void> delete(
            @PathVariable Long id
    ) {
        carService.delete(id);
        return new ApiResponse<>();
    }
}
