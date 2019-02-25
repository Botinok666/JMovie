package com.sfedu.JMovie.db;

import java.util.Arrays;

public enum RoleType {
    ROLE_ADMIN, ROLE_USER;
    public static RoleType findByType(String type) {
        return Arrays.stream(RoleType.values())
                .filter(x -> x.name().equals(type))
                .findFirst()
                .orElse(null);
    }
}
