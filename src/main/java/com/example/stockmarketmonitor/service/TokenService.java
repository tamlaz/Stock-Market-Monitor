package com.example.stockmarketmonitor.service;


import com.example.stockmarketmonitor.config.StartupInfo;
import com.example.stockmarketmonitor.config.UserRole;
import com.example.stockmarketmonitor.domain.CustomUser;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final StartupInfo startupInfo;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, StartupInfo startupInfo) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.startupInfo = startupInfo;
    }

    public String generateJwt(CustomUser user){
        Instant now = Instant.now();
        Instant expiresAt = now.plus(86400000, ChronoUnit.MILLIS);
        String scope = user.getUserRoles().stream()
                .map(UserRole::getRole)
                .collect(Collectors.joining(" "));


        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(user.getEmail())
                .claim("roles", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

    public boolean isValid(Jwt accessToken) {
        Instant startUpTime = startupInfo.getStartupTime();
        Instant issuedAt = (Instant) accessToken.getClaims().get("iat");
        Instant expiresAt = (Instant) accessToken.getClaims().get("exp");
        Instant current = Instant.now();
        return (startUpTime.isBefore(issuedAt) && current.isBefore(expiresAt));
    }
}
