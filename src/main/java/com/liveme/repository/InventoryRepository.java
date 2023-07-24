package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liveme.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

}