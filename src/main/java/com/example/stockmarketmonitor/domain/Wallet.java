package com.example.stockmarketmonitor.domain;

import jakarta.persistence.*;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    @Column
    private Double balance;

    @OneToOne(mappedBy = "wallet")
    private CustomUser user;
}
