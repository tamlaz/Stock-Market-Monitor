package com.example.stockmarketmonitor.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomUserCommand {

    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
