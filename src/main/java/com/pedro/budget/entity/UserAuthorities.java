package com.pedro.budget.entity;

public enum UserAuthorities {
    USER("user"),
    ADMIN("admin");

    private final String authority;

    UserAuthorities(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
