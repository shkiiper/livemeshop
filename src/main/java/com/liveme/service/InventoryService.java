package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Inventory;
import com.liveme.repository.InventoryRepository;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public String createInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
        return "Inventory created successfully!";
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

}
