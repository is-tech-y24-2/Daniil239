package ru.itmo.kotiki.dao;


import org.springframework.data.repository.CrudRepository;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Color;

import java.util.List;

public interface CatDao extends CrudRepository<Cat, Integer> {
    Cat findByName(String name);

    List<Cat> findAllByColor(Color color);
}
