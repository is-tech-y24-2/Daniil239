package ru.itmo.kotiki.dto;


import java.sql.Date;

public record CatDto(
        int id,
        String breed,
        String name,
        Date birthday,
        Color color) {
}
