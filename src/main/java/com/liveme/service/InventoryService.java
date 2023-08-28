package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.liveme.entity.Inventory;
import com.liveme.entity.Product;
import com.liveme.entity.Warhouse;
import com.liveme.repository.InventoryRepository;
import com.liveme.repository.ProductRepository;
import com.liveme.repository.WarhouseRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private WarhouseRepository warhouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public String createInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
        return "Inventory created successfully!";
    }

    public void createInventoryForWarehouse(int warhouseId) {
        Warhouse warhouse = warhouseRepository.findById(warhouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new RuntimeException("No products available");
        }

        Inventory inventory = new Inventory();
        inventory.setWarhouse(warhouse);
        inventory.setProducts(new HashSet<>(products));

        inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(int id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory updateInventory(int id, Inventory inventory) {
        inventory.setId(id);
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(int id) {
        inventoryRepository.deleteById(id);
    }

}
