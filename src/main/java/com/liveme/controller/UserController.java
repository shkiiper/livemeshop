package com.liveme.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.liveme.dto.AuthRequest;
import com.liveme.entity.UserInfo;
import com.liveme.service.JwtService;
import com.liveme.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    // @PostMapping("/auth")
    // public ResponseEntity<Map<String, Object>>
    // authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(authRequest.getName(),
    // authRequest.getPassword()));

    // if (authentication.isAuthenticated()) {
    // String username = authRequest.getName();
    // String accessToken = jwtService.generateToken(username);
    // String refreshToken = jwtService.generateRefreshToken(username);

    // Map<String, Object> response = new HashMap<>();
    // response.put("user", service.getUserByName(username));
    // response.put("accessToken", accessToken);
    // response.put("refreshToken", refreshToken);

    // return ResponseEntity.ok(response);
    // } else {
    // throw new UsernameNotFoundException("Invalid user request!");
    // }
    // }

    @GetMapping("/current-user")
    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return service.getUserByName(username);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable int id) {
        UserInfo user = service.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody
    // UserInfo userInfo) {
    // UserInfo existingUser = service.getUserById(id);
    // if (existingUser != null) {
    // userInfo.setId(id);
    // service.updateUser(userInfo);
    // return ResponseEntity.ok("User updated successfully");
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }
    @PatchMapping
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserInfo userInfo,
            Authentication authentication) {
        // Получаем имя текущего аутентифицированного пользователя из токена
        String currentUsername = authentication.getName();

        try {
            // Обновляем данные пользователя в базе данных и получаем Map с новым именем и
            // Access Token
            Map<String, Object> response = service.updateUser(currentUsername, userInfo);

            // Возвращаем Map с информацией об обновленном имени и новом Access Token в
            // ответе
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Возвращаем сообщение об ошибке с кодом 400 (Bad Request)
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        UserInfo existingUser = service.getUserById(id);
        if (existingUser != null) {
            service.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
