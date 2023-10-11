package com.example.stockmarketmonitor.domain;

import com.example.stockmarketmonitor.config.UserRole;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Data
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "user_role")
    private List<UserRole> userRoles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @ElementCollection
    private Set<Long> watchList = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
