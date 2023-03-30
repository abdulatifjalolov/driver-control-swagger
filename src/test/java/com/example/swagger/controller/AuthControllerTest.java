package com.example.swagger.controller;



import com.example.swagger.controller.request.UserRequestDTO;
import com.example.swagger.entity.UserEntity;
import com.example.swagger.entity._enum.PermissionEnum;
import com.example.swagger.entity._enum.RoleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.lifecycle.Startables;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseTest {
    private final static String PATH_REGISTER = "/api/auth/register";
    private final static String PATH_LOGIN = "/api/auth/login";

    @BeforeAll
    static void beforeAll() {
        Startables.deepStart(postgres).join();
    }

    @AfterEach
    void afterAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("registerUserShouldReturnOKStatus")
    public void register() throws Exception {
        callAdd().andExpect(status().isCreated());
    }

    @Test
    @DisplayName("registerUserShouldThrowEmailExist")
    public void registerThrow() throws Exception {
        callAdd();
        callAdd().andExpect(status().isConflict());
    }

    @Test
    @DisplayName("loginWithValidCredentialsShouldReturnToken")
    public void login() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("abdulatif@gmail.com");
        userEntity.setPassword(passwordEncoder.encode("12345678"));
        userEntity.setRoleEnumList(Collections.singletonList(RoleEnum.ADMIN));
        userEntity.setPermissionEnumList(Collections.singletonList(PermissionEnum.READ));
        userRepository.save(userEntity);

        callLogin().andExpect(status().isOk());
    }

    @Test
    @DisplayName("loginWithValidCredentialsShouldNotFound")
    public void loginThrow() throws Exception {
        callLogin().andExpect(status().isNotFound());
    }

    private ResultActions callLogin() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("abdulatif@gmail.com");
        userRequestDTO.setPassword("12345678");

        final MockHttpServletRequestBuilder request = post(PATH_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO));

        return mockMvc.perform(request);
    }

    private ResultActions callAdd() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("abdulatif@gmail.com");
        userRequestDTO.setPassword("123456789");
        userRequestDTO.setRoles(Collections.singletonList(RoleEnum.ADMIN));
        userRequestDTO.setPermissions(Collections.singletonList(PermissionEnum.READ));

        final MockHttpServletRequestBuilder request = post(PATH_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO));

        return mockMvc.perform(request);
    }
}