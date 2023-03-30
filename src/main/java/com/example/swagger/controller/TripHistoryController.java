package com.example.swagger.controller;

import com.example.swagger.config.swagger.SwaggerConfig;
import com.example.swagger.controller.response.ApiResponse;
import com.example.swagger.entity.model.dto.TripHistoryItemDTO;
import com.example.swagger.service.TripHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('CREATE')")
    public ApiResponse<Void> create(
            @Valid @RequestBody TripHistoryItemDTO tripHistoryItemDTO
    ) {
        tripHistoryService.create(tripHistoryItemDTO);
        return new ApiResponse<>();
    }
}
