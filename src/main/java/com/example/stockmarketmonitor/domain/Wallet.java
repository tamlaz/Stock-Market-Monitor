package com.example.stockmarketmonitor.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    @Column
    private Double balance;

    @OneToOne(mappedBy = "wallet")
    private CustomUser user;

    @ElementCollection
    private Map<Long, Double> portfolio = new HashMap<>();

    public Wallet(Double balance, CustomUser user) {
        this.balance = balance;
        this.user = user;
    }
}
