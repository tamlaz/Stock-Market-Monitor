package com.example.stockmarketanalyzer.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomUserCommand {

    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
