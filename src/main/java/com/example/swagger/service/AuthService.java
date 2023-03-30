package com.example.swagger.service;

import com.example.swagger.common.exception.EmailAlreadyExistException;
import com.example.swagger.common.exception.RecordNotFountException;
import com.example.swagger.config.utils.JWTProvider;
import com.example.swagger.controller.request.UserLoginDTO;
import com.example.swagger.controller.request.UserRequestDTO;
import com.example.swagger.controller.response.TokenDTO;
import com.example.swagger.entity.UserEntity;
import com.example.swagger.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService , AuditorAware<String> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenDTO register(UserRequestDTO userRequestDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(userRequestDTO.getEmail());
        if (optionalUserEntity.isPresent()) {
            throw new EmailAlreadyExistException(MessageFormat.format("already registered {0}",userRequestDTO.getEmail()));
        }
        UserEntity userEntity = UserEntity.of(userRequestDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return new TokenDTO(
                JWTProvider.generateAccessToken(savedUserEntity),
                JWTProvider.generateRefreshToken(savedUserEntity)
        );
    }

    public TokenDTO login(UserLoginDTO userLoginDTO) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(userLoginDTO.getEmail());
        if (optionalUserEntity.isEmpty()) {
            throw new RecordNotFountException("username or password is incorrect");
        }
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), optionalUserEntity.get().getPassword())) {
            throw new RecordNotFountException("username or password is incorrect");
        }
        return new TokenDTO(
                JWTProvider.generateAccessToken(optionalUserEntity.get()),
                JWTProvider.generateRefreshToken(optionalUserEntity.get())
        );
    }

    public TokenDTO getAccessToken(String refreshToken) {
        Claims claims = JWTProvider.isValidRefreshToken(refreshToken);
        if (claims != null) {
            String email = claims.getSubject();
            Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
            if (optionalUserEntity.isPresent()) {
                return new TokenDTO(
                        JWTProvider.generateAccessToken(optionalUserEntity.get()),
                        null
                );
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new RecordNotFountException(
                MessageFormat.format("user not found for {0} ",email)
        ));
    }

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof String principal))
            return Optional.empty();
        return Optional.of(principal);
    }
}
