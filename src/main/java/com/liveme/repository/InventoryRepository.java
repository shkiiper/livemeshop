package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liveme.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

}