package com.example.swagger.controller;

import com.example.swagger.config.swagger.SwaggerConfig;
import com.example.swagger.controller.request.UserLoginDTO;
import com.example.swagger.controller.request.UserRequestDTO;
import com.example.swagger.controller.response.ApiResponse;
import com.example.swagger.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@SecurityRequirement(name = SwaggerConfig.BEARER)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registration new user")
    public ApiResponse<?> register(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        return new ApiResponse<>(
                authService.register(userRequestDTO)
        );
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Login when access token is expired")
    public ApiResponse<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ) {
        return new ApiResponse<>(
                authService.login(userLoginDTO)
        );
    }
    @PostMapping("/access/token")
    @Operation(summary = "To get new accessToken by refreshToken")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> getAccessTokenByRefreshToken(
            @RequestBody String refreshToken
    ){
        return new ApiResponse<>(
                authService.getAccessToken(refreshToken)
        );
    }
}
