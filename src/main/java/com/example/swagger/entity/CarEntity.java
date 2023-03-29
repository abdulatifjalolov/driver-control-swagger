package com.example.swagger.entity;

import com.example.swagger.entity.model.dto.request.CarRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "cars")
public class CarEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false,unique = true)
    private String stateNumber;
    @Column(nullable = false)
    private Integer modelYear;
    @Column(nullable = false)
    private Double mileage;

    public static CarEntity of(CarRequestDTO carRequestDTO) {
        return CarEntity.builder()
                .model(carRequestDTO.getModel())
                .mileage(carRequestDTO.getMileage())
                .modelYear(carRequestDTO.getModelYear())
                .name(carRequestDTO.getName())
                .stateNumber(carRequestDTO.getStateNumber())
                .build();
    }
}
