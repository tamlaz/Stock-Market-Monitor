package com.example.stockmarketanalyzer.dto.outgoing;


import com.example.stockmarketanalyzer.config.UserRole;
import com.example.stockmarketanalyzer.domain.CustomUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProfileDataDetails {
    private String firstName;
    private String lastName;
    private String email;
    private List<UserRole> roles;

    public ProfileDataDetails(CustomUser customUser) {
        setEmail(customUser.getEmail());
        setFirstName(customUser.getFirstName());
        setLastName(customUser.getLastName());
        setRoles(customUser.getUserRoles());
    }
}
