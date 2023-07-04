package com.example.stockmarketanalyzer.config;

public enum UserRole {

    ROLE_INVESTOR("INVESTOR"),
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
