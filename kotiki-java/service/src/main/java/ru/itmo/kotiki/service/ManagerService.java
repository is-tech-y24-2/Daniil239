package ru.itmo.kotiki.service;

import ru.itmo.kotiki.dao.CatDaoImpl;
import ru.itmo.kotiki.dao.entity.Cat;

import java.util.List;
import java.util.Objects;

public class ManagerService {

    CatDaoImpl catDao = new CatDaoImpl();

    public Cat findCat(int id){
        return catDao.findById(id);
    }

    public Cat findCatByName(String name){
        for (Cat cat : findAllCats()){
            if (Objects.equals(cat.getName(), name)) return cat;
        }
        return null;
    }

    public void saveCat(Cat cat){
        catDao.save(cat);
    }
    public void updateCat(Cat cat){
        catDao.update(cat);
    }
    public void deleteCat(Cat cat){
        catDao.delete(cat);
    }

    public List<Cat> findAllCats(){
        return catDao.findAll();
    }
}
