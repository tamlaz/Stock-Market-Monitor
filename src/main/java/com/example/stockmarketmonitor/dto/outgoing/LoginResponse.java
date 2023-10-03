package com.example.stockmarketmonitor.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String jwt;

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }
}
