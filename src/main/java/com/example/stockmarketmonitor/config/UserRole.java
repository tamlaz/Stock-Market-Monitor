package com.example.stockmarketmonitor.config;

public enum UserRole {

    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
