package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.liveme.entity.Thumbnail;
import com.liveme.entity.UserInfo;
import com.liveme.repository.UserInfoRepository;

@Service
public class UserService {

    @Autowired
    private UserInfoRepository repository;

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

    // public String updateUser(UserInfo userInfo) {
    // if (userInfo.getId() == 0) {
    // throw new IllegalArgumentException("User ID cannot be zero");
    // }

    // UserInfo existingUser = repository.findById(userInfo.getId()).orElse(null);
    // if (existingUser != null) {
    // existingUser.setName(userInfo.getName());
    // existingUser.setEmail(userInfo.getEmail());
    // existingUser.setPhone(userInfo.getPhone());
    // repository.save(existingUser);
    // return "User updated successfully";
    // } else {
    // throw new IllegalArgumentException("User not found with ID: " +
    // userInfo.getId());
    // }
    // }
    public String updateUser(String currentUsername, UserInfo updatedUserInfo) {
        UserInfo existingUser = repository.findByName(currentUsername).orElse(null);
        if (existingUser == null) {
            throw new IllegalArgumentException("Текущий пользователь не найден в базе данных.");
        }

        // Проверяем, какие поля переданы в объекте updatedUserInfo и обновляем их
        if (updatedUserInfo.getName() != null) {
            existingUser.setName(updatedUserInfo.getName());
        }
        if (updatedUserInfo.getEmail() != null) {
            existingUser.setEmail(updatedUserInfo.getEmail());
        }
        if (updatedUserInfo.getPhone() != null) {
            existingUser.setPhone(updatedUserInfo.getPhone());
        }

        repository.save(existingUser);

        return "Пользователь успешно обновлен";
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
