package com.example.stockmarketmonitor.dto.incoming;

import lombok.Data;

@Data
public class LoginCommand {

    private String email;
    private String password;
}
