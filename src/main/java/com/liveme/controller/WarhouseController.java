package com.liveme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liveme.entity.Warhouse;
import com.liveme.exception.BadRequestException;
import com.liveme.service.WarhouseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warehouse")
public class WarhouseController {
    private final WarhouseService warhouseService;

    @Autowired
    public WarhouseController(WarhouseService warhouseService) {
        this.warhouseService = warhouseService;
    }

    @GetMapping
    public ResponseEntity<?> getAllWarhouses() {
        try {
            List<Warhouse> warhouses = warhouseService.getAllWarhouses();
            return ResponseEntity.ok(warhouses);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWarhouseById(@PathVariable int id) {
        try {
            Warhouse warhouse = warhouseService.getWarhouseById(id);
            return ResponseEntity.ok(warhouse);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> createWarhouse(@RequestBody Warhouse warhouse) {
        try {
            Warhouse createdWarhouse = warhouseService.createWarhouse(warhouse);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Warehouse created");
            return ResponseEntity.ok(successResponse);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateWarhouse(@PathVariable int id, @RequestBody Warhouse warhouse) {
        try {
            Warhouse updatedWarhouse = warhouseService.updateWarhouse(id, warhouse);
            return ResponseEntity.ok(updatedWarhouse);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWarhouse(@PathVariable int id) {
        try {
            warhouseService.deleteWarhouse(id);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Warhouse deleted");
            return ResponseEntity.ok(successResponse);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
