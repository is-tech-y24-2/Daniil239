package ru.itmo.kotiki.service;

import ru.itmo.kotiki.dao.CatDaoImpl;
import ru.itmo.kotiki.dao.OwnerDaoImpl;
import ru.itmo.kotiki.entity.Cat;
import ru.itmo.kotiki.entity.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OwnerService {

    private OwnerDaoImpl ownerDao = new OwnerDaoImpl();
    private CatDaoImpl catDao = new CatDaoImpl();
    private ManagerService managerService = new ManagerService();

    public OwnerService(){
    }

    public Owner findOwner(int id){
        return ownerDao.findById(id);
    }

    public void saveOwner(Owner owner){
        ownerDao.save(owner);
    }

    public void updateOwner(Owner owner){
        ownerDao.update(owner);
    }

    public void deleteOwner(Owner owner){
        ownerDao.delete(owner);
    }

    public List<Owner> findAllOwners(){
        return ownerDao.findAll();
    }

    public Owner findOwnerByName(String name){
        Owner res = null;
        for (Owner owner : findAllOwners()){
            if (Objects.equals(owner.getName(), name)){
                res = owner;
            }
        }
        return res;
    }

    public List<Cat> findFreeCats(){
        List<Cat> result = new ArrayList<>();
        for(Cat cat : catDao.findAll()){
            if(cat.getOwner() == null){
                result.add(cat);
            }
        }
        return result;
    }

    public List<Cat> findCatsByOwnerId(int id){
        List<Cat> result = new ArrayList<>();
        for(Cat cat : catDao.findAll()){
            if(cat.getOwner().getId() == id){
                result.add(cat);
            }
        }
        return result;
    }

    public void takeCat(Owner owner, Cat cat){
        owner.addCat(cat);
        this.updateOwner(owner);
        managerService.updateCat(cat);
    }

    public void returnCat(Owner owner, Cat cat){
        try{
            owner.removeCat(cat);
            cat.setOwner(null);
            this.updateOwner(owner);
            managerService.updateCat(cat);
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
