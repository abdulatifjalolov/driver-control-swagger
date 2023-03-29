package com.example.swagger.controller;

import com.example.swagger.config.swagger.SwaggerConfig;
import com.example.swagger.controller.response.ApiResponse;
import com.example.swagger.entity.model.dto.request.TripHistoryItemDTO;
import com.example.swagger.service.TripHistoryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip/item")
@SecurityRequirement(name = SwaggerConfig.BEARER)
public class TripHistoryItemController {
    private final TripHistoryItemService tripHistoryItemService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new trip item")
    public ApiResponse<Void> create(
            @Valid @RequestBody TripHistoryItemDTO tripHistoryItemDTO
    ) {
        tripHistoryItemService.create(tripHistoryItemDTO);
        return new ApiResponse<>();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "To get trip item")
    public ApiResponse<?> get(
            @PathVariable Long id
    ) {
        return new ApiResponse<>(
                tripHistoryItemService.get(id)
        );
    }
}
