package com.liveme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.liveme.entity.Warhouse;
import com.liveme.service.WarhouseService;

import java.util.List;

@RestController
@RequestMapping("/warhouse")
public class WarhouseController {
    private final WarhouseService warhouseService;

    @Autowired
    public WarhouseController(WarhouseService warhouseService) {
        this.warhouseService = warhouseService;
    }

    @PostMapping("/create")
    public String createWarhouse(@RequestBody Warhouse warhouse) {
        return warhouseService.createWarhouse(warhouse);
    }

    @GetMapping("/all")
    public List<Warhouse> getAllWarhouses() {
        return warhouseService.getAllWarhouses();
    }
}