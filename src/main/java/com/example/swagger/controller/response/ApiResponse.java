package com.example.swagger.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Response payload")
public class ApiResponse<T> {
    private String message;
    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }
}
