package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liveme.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
