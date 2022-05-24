package ru.itmo.kotiki.dto;

import java.sql.Date;

public record OwnerDto(
        int id,
        String name,
        Date birthday) {
}
