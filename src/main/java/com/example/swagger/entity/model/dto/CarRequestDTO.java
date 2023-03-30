package com.example.swagger.entity.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Car create payload")
public class CarRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "model is required")
    private String model;

    @NotBlank(message = "model is required")
    @Length(min = 8, max = 8, message = "state number length should be equal to 8")
    private String stateNumber;

    @NotNull(message = "model year is required")
    private Integer modelYear;

    @NotNull(message = "mileage is required")
    private Double mileage;
}
