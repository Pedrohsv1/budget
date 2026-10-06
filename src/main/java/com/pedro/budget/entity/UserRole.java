package com.pedro.budget.entity;

public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getrole() {
        return role;
    }
}
