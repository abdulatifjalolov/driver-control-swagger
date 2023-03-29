package com.example.swagger.entity.model.dto.response;

import com.example.swagger.entity.TripHistoryItemEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarResponseDTO {
    private String name;
    private String model;
    private String stateNumber;
    private Integer modelYear;
    private Double mileage;

    private String createdBy;

    private String lastModifiedBy;
    private Timestamp createdAt;
    private Timestamp lastModifiedAt;

    private List<TripHistoryItemEntity> tripHistoryItemEntities;
}
