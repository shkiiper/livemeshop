package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Role;
import com.liveme.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public String createRole(Role role) {
        roleRepository.save(role);
        return "Role created successfully!";
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Можно добавить пользовательские методы, если требуется дополнительная логика
}
