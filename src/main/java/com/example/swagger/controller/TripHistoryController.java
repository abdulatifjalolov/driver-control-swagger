package com.example.swagger.controller;

import com.example.swagger.config.swagger.SwaggerConfig;
import com.example.swagger.controller.response.ApiResponse;
import com.example.swagger.entity.model.dto.request.TripHistoryDTO;
import com.example.swagger.service.TripHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
@SecurityRequirement(name = SwaggerConfig.BEARER)
public class TripHistoryController {
    private final TripHistoryService tripHistoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "To create new trip history")
    public ApiResponse<Void> create(
            @Valid @RequestBody TripHistoryDTO tripHistoryDTO
    ) {
        tripHistoryService.create(tripHistoryDTO);
        return new ApiResponse<>();
    }
}
