package com.example.swagger.entity.model.dto.request;

import com.example.swagger.entity.TripHistoryItemEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Trip history payload")
public class TripHistoryDTO {
    @NotBlank(message = "carId is required")
    private Long carId;
    private List<TripHistoryItemEntity> tripHistoryItemEntities;
}
