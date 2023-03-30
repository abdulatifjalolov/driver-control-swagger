package com.example.swagger.entity.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Trip history item payload")
public class TripHistoryItemDTO {

    @NotNull(message = "carId is required")
    @Schema(defaultValue = "1")
    private Long carId;

    private Timestamp departureDate;

    @NotBlank(message = "departureAddress is required")
    @Schema(defaultValue = "street-1")
    private String departureAddress;

    private Timestamp arrivalDate;

    @NotBlank(message = "arrivalAddress is required")
    @Schema(defaultValue = "street-2")
    private String arrivalAddress;
}
