package com.liveme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liveme.entity.Warhouse;
import com.liveme.service.WarhouseService;

import java.util.List;

@RestController
@RequestMapping("/warhous")
public class WarhouseController {
    private final WarhouseService warhouseService;

    @Autowired
    public WarhouseController(WarhouseService warhouseService) {
        this.warhouseService = warhouseService;
    }

    @PostMapping
    public ResponseEntity<String> createWarhouse(@RequestBody Warhouse warhouse) {
        warhouseService.createWarhouse(warhouse);
        return new ResponseEntity<>("Warhouse created successfully!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Warhouse>> getAllWarhouses() {
        List<Warhouse> warehouses = warhouseService.getAllWarhouses();
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warhouse> getWarhouseById(@PathVariable int id) {
        Warhouse warhouse = warhouseService.getWarhouseById(id);
        if (warhouse != null) {
            return new ResponseEntity<>(warhouse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warhouse> updateWarhouse(@PathVariable int id, @RequestBody Warhouse warhouse) {
        Warhouse updatedWarhouse = warhouseService.updateWarhouse(id, warhouse);
        if (updatedWarhouse != null) {
            return new ResponseEntity<>(updatedWarhouse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarhouse(@PathVariable int id) {
        warhouseService.deleteWarhouse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
