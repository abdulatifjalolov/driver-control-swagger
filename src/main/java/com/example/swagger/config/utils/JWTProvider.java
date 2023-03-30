package com.example.swagger.config.utils;

import com.example.swagger.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class JWTProvider {
    static String jwtAccessSecretKey = "jwtAccessSecretKey";
    static String jwtRefreshSecretKey = "jwtRefreshSecretKey";
    static long expirationAccessTime = 1_000 * 60 * 30; //30 min
    static long expirationRefreshTime = 1_000 * 60 * 60 * 24; //24 hour


    public static synchronized String generateAccessToken(
            UserEntity userEntity
    ) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecretKey)
                .setSubject(userEntity.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationAccessTime))
                .claim("authorities", userEntity.getAuthorities())
                .compact();
    }
    public static synchronized String generateRefreshToken(
            UserEntity userEntity
    ) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecretKey)
                .setSubject(userEntity.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationRefreshTime))
                .compact();
    }


    public static synchronized Claims isValidAccessToken(String token) {
        return getAccessClaim(token);
    }

    public static synchronized Claims isValidRefreshToken(String token) {
        return getRefreshClaim(token);
    }
    public static List<LinkedHashMap<String, String>> getAuthorities(Claims claims) {
        return (List<LinkedHashMap<String, String>>) claims.get("authorities");
    }
    private static synchronized Claims getAccessClaim(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(jwtAccessSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static synchronized Claims getRefreshClaim(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(jwtRefreshSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
