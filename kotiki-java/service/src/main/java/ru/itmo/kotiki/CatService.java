package ru.itmo.kotiki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.CatDao;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Color;
import ru.itmo.kotiki.dto.CatDto;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CatService implements ICatService {

    private final CatDao catDao;

    @Autowired
    public CatService(CatDao catDao) {
        this.catDao = catDao;
    }

    @Override
    public CatDto findCat(int id) {
        Cat cat = catDao.findById(id).get();
        return CatDto.builder()
                .id(cat.getId())
                .breed(cat.getBreed())
                .name(cat.getName())
                .birthday(cat.getBirthday())
                .color(cat.getColor())
                .build();
    }

    @Override
    public CatDto findCatByName(String name) {
        Cat cat = catDao.findByName(name);
        return CatDto.builder()
                .id(cat.getId())
                .breed(cat.getBreed())
                .name(cat.getName())
                .birthday(cat.getBirthday())
                .color(cat.getColor())
                .build();
    }

    @Override
    public void saveCat(Cat cat) {
        catDao.save(cat);
    }

    @Override
    public void updateCat(Cat cat) {
        catDao.save(cat);
    }

    @Override
    public void deleteCat(Cat cat) {
        catDao.delete(cat);
    }

    @Override
    public List<CatDto> findAllCats() {
        List<Cat> casts = StreamSupport.stream(catDao.findAll().spliterator(), false).toList();

        return casts
                .stream()
                .map(cat -> CatDto.builder()
                        .id(cat.getId())
                        .breed(cat.getBreed())
                        .name(cat.getName())
                        .birthday(cat.getBirthday())
                        .color(cat.getColor())
                        .build())
                .toList();
    }

    @Override
    public List<CatDto> findAllCatsBy(String color) {

        return catDao.findAllByColor(Color.valueOf(color.toUpperCase()))
                .stream()
                .map(cat -> CatDto.builder()
                        .id(cat.getId())
                        .breed(cat.getBreed())
                        .name(cat.getName())
                        .birthday(cat.getBirthday())
                        .color(cat.getColor())
                        .build())
                .toList();
    }
}
