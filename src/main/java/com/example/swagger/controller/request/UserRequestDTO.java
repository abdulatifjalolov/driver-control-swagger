package com.example.swagger.controller.request;



import com.example.swagger.entity._enum.PermissionEnum;
import com.example.swagger.entity._enum.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Registration payload")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestDTO {

    @Email(message = "Email should be valid")
    @Schema(defaultValue = "abdulatifjalolov6004273@gmail.com")
    @NotBlank(message = "email is required")
    private String email;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    private List<RoleEnum> roles;
    private List<PermissionEnum> permissions;

    @JsonIgnore
    public boolean isUser() {
        return roles == null && permissions == null;
    }
}

