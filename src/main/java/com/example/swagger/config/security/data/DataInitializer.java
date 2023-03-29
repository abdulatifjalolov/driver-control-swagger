package com.example.swagger.config.security.data;

import com.example.swagger.entity._enum.PermissionEnum;
import com.example.swagger.entity._enum.RoleEnum;
import com.example.swagger.entity.UserEntity;
import com.example.swagger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        //add SUPER_ADMIN when application is starting
        if (!userRepository.existsByEmail("superadmin@gmail.com")) {
            UserEntity superAdmin = UserEntity.builder()
                    .email("superadmin@gmail.com")
                    .password(passwordEncoder.encode("superadmin"))
                    .roleEnumList(Collections.singletonList(RoleEnum.SUPER_ADMIN))
                    .permissionEnumList(Arrays.asList(PermissionEnum.CREATE, PermissionEnum.READ, PermissionEnum.UPDATE, PermissionEnum.DELETE))
                    .build();
            userRepository.save(superAdmin);
        }
    }
}
