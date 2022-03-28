package ru.itmo.kotiki.dao;

import ru.itmo.kotiki.entity.Cat;

import java.util.List;

public interface CatDao {
    public Cat findById(int id);
    public void save(Cat cat);
    public void update(Cat cat);
    public void delete(Cat cat);
    public List<Cat> findAll();
}
