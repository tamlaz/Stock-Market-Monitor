package com.example.stockmarketmonitor.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Wallet(Double balance, CustomUser user) {
        this.balance = balance;
        this.user = user;
    }
}
