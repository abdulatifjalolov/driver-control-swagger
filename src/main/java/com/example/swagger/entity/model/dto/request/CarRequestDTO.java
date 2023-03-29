package com.example.swagger.entity.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
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
    @Length(min = 8,max = 8)
    private String stateNumber;
    private Integer modelYear;
    private Double mileage;
}
