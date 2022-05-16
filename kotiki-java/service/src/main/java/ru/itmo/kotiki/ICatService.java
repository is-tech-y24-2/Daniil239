package ru.itmo.kotiki;

import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dto.CatDto;

import java.util.List;

public interface ICatService {
    CatDto findCat(int id);

    CatDto findCatByName(String name);

    void saveCat(Cat cat);

    void updateCat(Cat cat);

    void deleteCat(Cat cat);

    List<CatDto> findAllCats();

    List<CatDto> findAllCatsBy(String color);
}
