package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liveme.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
