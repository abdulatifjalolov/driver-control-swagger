package com.example.swagger.entity;

import com.example.swagger.controller.request.UserRequestDTO;
import com.example.swagger.entity._enum.PermissionEnum;
import com.example.swagger.entity._enum.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity extends BaseEntity implements UserDetails {

    @NonNull
    private String email;

    @NonNull
    private String password;
    @Enumerated(EnumType.STRING)
    private List<RoleEnum> roleEnumList;
    @Enumerated(EnumType.STRING)
    private List<PermissionEnum> permissionEnumList;

    public static UserEntity of(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.isUser()) {
            return UserEntity
                    .builder()
                    .email(userRequestDTO.getEmail())
                    .password(userRequestDTO.getPassword())
                    .build();
        }
        return UserEntity
                .builder()
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .roleEnumList(userRequestDTO.getRoles())
                .permissionEnumList(userRequestDTO.getPermissions())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roleEnumList.forEach((role) ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()))
        );
        permissionEnumList.forEach((permission) ->
                authorities.add(new SimpleGrantedAuthority(permission.name()))
        );
        return authorities;
    }


    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
