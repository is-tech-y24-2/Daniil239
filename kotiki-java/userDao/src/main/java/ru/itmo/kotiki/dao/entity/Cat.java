package ru.itmo.kotiki.dao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.itmo.kotiki.dto.Color;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "cats")
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "owner")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String breed;

    @Column
    private String name;

    @Column
    private Date birthday;

    @Column (name="color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Cat(){
    }
}
