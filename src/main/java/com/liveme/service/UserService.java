package com.liveme.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.liveme.entity.Thumbnail;
import com.liveme.entity.UserInfo;
import com.liveme.repository.UserInfoRepository;

@Service
public class UserService {

    private final UserInfoRepository repository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserInfoRepository repository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(UserInfo userInfo) {
        String username = userInfo.getName();
        String email = userInfo.getEmail();

        if (repository.findByName(username).isPresent()) {
            throw new IllegalArgumentException("Имя пользователя уже существует.");
        }

        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Адрес электронной почты уже существует.");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Пользователь добавлен в систему";
    }

    public UserInfo getUserByName(String username) {
        return repository.findByName(username).orElse(null);
    }

    public UserInfo getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Map<String, Object> updateUser(String currentUsername, UserInfo updatedUserInfo) {
        UserInfo existingUser = repository.findByName(currentUsername).orElse(null);
        if (existingUser == null) {
            throw new IllegalArgumentException("Текущий пользователь не найден в базе данных.");
        }

        if (updatedUserInfo.getName() != null) {
            existingUser.setName(updatedUserInfo.getName());
        }
        if (updatedUserInfo.getEmail() != null) {
            existingUser.setEmail(updatedUserInfo.getEmail());
        }
        if (updatedUserInfo.getPhone() != null) {
            existingUser.setPhone(updatedUserInfo.getPhone());
        }

        existingUser = repository.save(existingUser);

        String newAccessToken = jwtService.generateToken(existingUser.getName());

        Map<String, Object> response = new HashMap<>();
        response.put("username", existingUser.getName());
        response.put("email", existingUser.getEmail());
        response.put("phone", existingUser.getPhone());
        response.put("accessToken", newAccessToken);

        return response;
    }

    public String deleteUser(int id) {
        UserInfo existingUser = repository.findById(id).orElse(null);
        if (existingUser != null) {
            repository.deleteById(id);
            return "User deleted successfully";
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }
}
