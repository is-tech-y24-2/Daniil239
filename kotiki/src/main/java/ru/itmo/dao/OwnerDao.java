package ru.itmo.dao;

import ru.itmo.entity.Owner;

import java.util.List;

public interface OwnerDao {
    public Owner findById(int id);
    public void save(Owner owner);
    public void update(Owner owner);
    public void delete(Owner owner);
    public List<Owner> findAll();
}
