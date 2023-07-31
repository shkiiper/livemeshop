package com.liveme.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.liveme.dto.AuthRequest;
import com.liveme.entity.UserInfo;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
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

    // @PostMapping
    // public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
    // try {
    // service.addNewUser(userInfo);
    // return ResponseEntity.ok("Пользователь добавлен в систему.");
    // } catch (BadRequestException ex) {
    // Map<String, String> errorResponse = new HashMap<>();
    // errorResponse.put("status", ex.getStatus());
    // errorResponse.put("errorMessage", ex.getErrorMessage());
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    // } catch (SuccessException ex) {
    // Map<String, String> successResponse = new HashMap<>();
    // successResponse.put("status", ex.getStatus());
    // successResponse.put("message", ex.getMessage());
    // return ResponseEntity.ok(successResponse);
    // }
    // }

    // @GetMapping("/current-user")
    // public UserInfo getCurrentUser() {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // String username = authentication.getName();

    // return service.getUserByName(username);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<UserInfo> getUserById(@PathVariable int id) {
    // UserInfo user = service.getUserById(id);
    // if (user != null) {
    // return ResponseEntity.ok(user);
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }

    // @PatchMapping
    // public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserInfo
    // userInfo,
    // Authentication authentication) {
    // String currentUsername = authentication.getName();

    // try {
    // Map<String, Object> response = service.updateUser(currentUsername, userInfo);
    // return ResponseEntity.ok(response);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().body(null);
    // }
    // }

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
