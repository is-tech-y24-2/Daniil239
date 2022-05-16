package ru.itmo.kotiki.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.itmo.kotiki.dao.entity.Color;

import java.sql.Date;

@Data
@Builder
public class CatDto {
    private int id;
    private String breed;
    private String name;
    private Date birthday;
    private Color color;
}
