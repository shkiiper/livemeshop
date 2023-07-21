package com.javatechie.repository;

import com.javatechie.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Можно добавить пользовательские методы, если требуется дополнительная логика
}
