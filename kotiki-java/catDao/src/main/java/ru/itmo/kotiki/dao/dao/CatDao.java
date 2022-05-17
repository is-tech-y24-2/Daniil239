package ru.itmo.kotiki.dao.dao;


import org.springframework.data.repository.CrudRepository;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Color;

import java.util.List;

import java.util.List;

import java.util.List;

public interface CatDao extends CrudRepository<Cat, Integer> {
    List<Cat> findAllByOwnerId(int ownerId);

    Cat findByIdAndOwnerId(int id, int ownerId);

    Cat findByNameAndOwnerId(String name, int ownerId);

    List<Cat> findAllByColorAndOwnerId(Color color, Integer ownerId);
}
