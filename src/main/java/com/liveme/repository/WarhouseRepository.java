package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liveme.entity.Warhouse;

public interface WarhouseRepository extends JpaRepository<Warhouse, Integer> {

}