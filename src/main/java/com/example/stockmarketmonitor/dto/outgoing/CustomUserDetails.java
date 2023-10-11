package com.example.stockmarketmonitor.dto.outgoing;


import com.example.stockmarketmonitor.domain.CustomUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CustomUserDetails {
    private String firstName;
    private String lastName;
    private String email;
    private Set<StockListItem> watchList;

    public CustomUserDetails(CustomUser customUser) {
        this.firstName = customUser.getFirstName();
        this.lastName = customUser.getLastName();
        this.email = customUser.getEmail();
        this.watchList = customUser.getWatchList().stream()
                .map(StockListItem::new).collect(Collectors.toSet());
    }
}
