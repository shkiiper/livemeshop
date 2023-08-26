package com.liveme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liveme.entity.Inventory;
import com.liveme.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<String> createInventory(@RequestBody Inventory inventory) {
        inventoryService.createInventory(inventory);
        return new ResponseEntity<>("Inventory created successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/{warehouseId}")
    public ResponseEntity<String> createInventoryForWarehouse(@PathVariable int warehouseId) {
        inventoryService.createInventoryForWarehouse(warehouseId);
        return ResponseEntity.ok("Inventory created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = inventoryService.getAllInventories();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable int id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        if (inventory != null) {
            return new ResponseEntity<>(inventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable int id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);
        if (updatedInventory != null) {
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable int id) {
        inventoryService.deleteInventory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
