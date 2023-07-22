package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Warhouse;
import com.liveme.repository.WarhouseRepository;

import java.util.List;

@Service
public class WarhouseService {
    private final WarhouseRepository warhouseRepository;

    @Autowired
    public WarhouseService(WarhouseRepository warhouseRepository) {
        this.warhouseRepository = warhouseRepository;
    }

    public String createWarhouse(Warhouse warhouse) {
        warhouseRepository.save(warhouse);
        return "Warhouse created successfully!";
    }

    public List<Warhouse> getAllWarhouses() {
        return warhouseRepository.findAll();
    }

}
