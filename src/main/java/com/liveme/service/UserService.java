package com.liveme.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.liveme.entity.Thumbnail;
import com.liveme.entity.UserInfo;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import com.liveme.repository.UserInfoRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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

    public void addNewUser(UserInfo userInfo) throws BadRequestException, SuccessException {
        String username = userInfo.getName();
        String email = userInfo.getEmail();

        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Ошибка", "Имя пользователя не может быть пустым.");
        }

        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Ошибка", "Адрес электронной почты не может быть пустым.");
        }

        if (repository.findByName(username).isPresent()) {
            throw new BadRequestException("Ошибка", "Имя пользователя уже существует.");
        }

        if (repository.findByEmail(email).isPresent()) {
            throw new BadRequestException("Ошибка", "Адрес электронной почты уже существует.");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);

        throw new SuccessException("Успешно", "Пользователь добавлен в систему.");
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
