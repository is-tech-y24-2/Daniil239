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
public class CatService {

    private final CatDao catDao;

    @Autowired
    public CatService(CatDao catDao) {
        this.catDao = catDao;
    }

    public CatDto findCat(int id) {
        Cat cat = catDao.findById(id).get();
        return map(cat);
    }

    public CatDto findCatByIdAndOwnerId(int id, int ownerId) {
        Cat cat = catDao.findByIdAndOwnerId(id, ownerId);
        return map(cat);
    }

    public CatDto findCatByName(String name) {
        Cat cat = catDao.findByName(name);
        return map(cat);
    }

    public void saveCat(Cat cat) {
        catDao.save(cat);
    }

    public void updateCat(Cat cat) {
        catDao.save(cat);
    }

    public void deleteCat(Cat cat) {
        catDao.delete(cat);
    }

    public List<CatDto> findAllCats() {
        List<Cat> casts = StreamSupport.stream(catDao.findAll().spliterator(), false).toList();

        return casts
                .stream()
                .map(this::map)
                .toList();
    }

    public List<CatDto> findAllCatsByOwnerId(int ownerId) {
        List<Cat> casts = catDao.findAllByOwnerId(ownerId).stream().toList();

        return casts
                .stream()
                .map(this::map)
                .toList();
    }

    public List<CatDto> findAllCatsByColorAndOwnerId(String color, int ownerId) {

        return catDao.findAllByColorAndOwnerId(Color.valueOf(color.toUpperCase()), ownerId)
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

    private CatDto map(Cat cat) {
        return CatDto.builder()
                .id(cat.getId())
                .breed(cat.getBreed())
                .name(cat.getName())
                .birthday(cat.getBirthday())
                .color(cat.getColor())
                .build();
    }
}
