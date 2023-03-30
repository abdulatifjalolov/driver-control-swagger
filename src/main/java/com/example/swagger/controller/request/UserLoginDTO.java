package com.example.swagger.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Login payload")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginDTO {
    @Email(message = "Email should be valid")
    @Schema(defaultValue = "superadmin@gmail.com")
    private String email;

    @Schema(defaultValue = "superadmin")
    private String password;
}
