package com.example.stockmarketmonitor.service;


import com.example.stockmarketmonitor.config.StartupInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private StartupInfo startupInfo;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, StartupInfo startupInfo) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.startupInfo = startupInfo;
    }

    public String generateJwt(Authentication auth){
        Instant now = Instant.now();
        Instant expiresAt = now.plus(86400000, ChronoUnit.MILLIS);
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));


        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(auth.getName())
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
