package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Warhouse;
import com.liveme.exception.BadRequestException;
import com.liveme.repository.WarhouseRepository;

import java.util.List;

@Service
public class WarhouseService {
    private final WarhouseRepository warhouseRepository;

    @Autowired
    public WarhouseService(WarhouseRepository warhouseRepository) {
        this.warhouseRepository = warhouseRepository;
    }

    public Warhouse createWarhouse(Warhouse warhouse) throws BadRequestException {
        if (warhouse == null || warhouse.getRegion() == null || warhouse.getRegion().isEmpty()) {
            throw new BadRequestException("Ошибка", "Invalid warhouse data", "warhouse");
        }
        Warhouse existingWarhouse = warhouseRepository.findByRegion(warhouse.getRegion());
        if (existingWarhouse != null) {
            throw new BadRequestException("Ошибка", "такой склад уже существует", "warhouse");
        }

        Warhouse createdWarhouse = warhouseRepository.save(warhouse);

        return createdWarhouse;
    }

    public List<Warhouse> getAllWarhouses() throws BadRequestException {
        List<Warhouse> warhouses = warhouseRepository.findAll();
        if (warhouses.isEmpty()) {
            throw new BadRequestException("Ошибка", "Нет доступных складов", "warhouses");
        }
        return warhouses;
    }

    public Warhouse getWarhouseById(int id) throws BadRequestException {
        Warhouse warhouse = warhouseRepository.findById(id).orElse(null);
        if (warhouse == null) {
            throw new BadRequestException("Ошибка", "Склад с указанным ID не найден", "id");
        }
        return warhouse;
    }

    public Warhouse updateWarhouse(int id, Warhouse warhouse) throws BadRequestException {
        if (warhouse == null || warhouse.getRegion() == null || warhouse.getRegion().isEmpty()) {
            throw new BadRequestException("Ошибка", "Invalid warhouse data", "warhouse");
        }

        Warhouse existingWarhouse = warhouseRepository.findById(id).orElse(null);
        if (existingWarhouse == null) {
            throw new BadRequestException("Ошибка", "Склад с указанным ID не найден", "id");
        }

        warhouse.setId(id);
        Warhouse updatedWarhouse = warhouseRepository.save(warhouse);

        return updatedWarhouse;
    }

    public void deleteWarhouse(int id) throws BadRequestException {
        Warhouse existingWarhouse = warhouseRepository.findById(id).orElse(null);
        if (existingWarhouse == null) {
            throw new BadRequestException("Ошибка", "Склад с указанным ID не найден", "id");
        }

        warhouseRepository.deleteById(id);
    }

}
