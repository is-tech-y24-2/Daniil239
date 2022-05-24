package ru.itmo.kotiki.dto;

import java.sql.Date;

public record AuthUser(
        String name,
        String password,
        boolean enabled,
        String role,
        String fullName,
        Date birthday
) {
}
