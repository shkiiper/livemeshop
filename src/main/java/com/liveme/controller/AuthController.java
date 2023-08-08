package com.liveme.controller;

import com.liveme.dto.AuthRequest;
import com.liveme.entity.UserInfo;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.SuccessException;
import com.liveme.service.JwtService;
import com.liveme.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String refreshToken = request.get("refreshToken");
            if (refreshToken == null || refreshToken.isEmpty()) {
                throw new BadRequestException("Ошибка", "Missing or empty 'refreshToken' field.", "refreshToken");
            }

            String username = jwtService.extractUsername(refreshToken);
            if (username == null || username.isEmpty()) {
                throw new BadRequestException("Ошибка", "Invalid refresh token.", "refreshToken");
            }

            String accessToken = jwtService.generateToken(username);
            String newRefreshToken = jwtService.generateRefreshToken(username);

            response.put("accessToken", accessToken);
            response.put("refreshToken", newRefreshToken);
        } catch (ExpiredJwtException e) {
            response.put("status", "Ошибка");
            response.put("errorMessage", "Токен истек");
            response.put("errorCode", "TOKEN_EXPIRED");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (BadRequestException e) {
            response.put("status", e.getStatus());
            response.put("errorMessage", e.getErrorMessage());
            response.put("fieldName", e.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                String username = authRequest.getName();
                String accessToken = jwtService.generateToken(username);
                String refreshToken = jwtService.generateRefreshToken(username);

                Map<String, Object> response = new HashMap<>();
                response.put("user", userService.getUserByName(username));
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);

                return ResponseEntity.ok(response);
            } else {
                throw new UsernameNotFoundException("Invalid user request.");
            }
        } catch (BadCredentialsException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "Ошибка");
            errorResponse.put("errorMessage", "Не верное имя пользователя или пароль");
            errorResponse.put("fieldName", "username/password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
        try {
            userService.addNewUser(userInfo);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", "Успешно");
            successResponse.put("message", "Пользователь добавлен в систему.");
            return ResponseEntity.ok(successResponse);
        } catch (BadRequestException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", ex.getStatus());
            errorResponse.put("errorMessage", ex.getErrorMessage());
            errorResponse.put("fieldName", ex.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (SuccessException ex) {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("status", ex.getStatus());
            successResponse.put("message", ex.getMessage());
            return ResponseEntity.ok(successResponse);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            LOGGER.debug("getCurrentUser() called for user: {}", username);
            UserInfo userInfo = userService.getUserByName(username);
            return ResponseEntity.ok(userInfo);
        } catch (ExpiredJwtException e) {
            LOGGER.error("Error occurred during getCurrentUser()", e);
            String errorMessage = "Токен истек. Пожалуйста, выполните повторную аутентификацию.";
            LOGGER.error("Error occurred during getCurrentUser()", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
    }

    @PatchMapping("me")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserInfo userInfo,
            Authentication authentication) {
        String currentUsername = authentication.getName();

        try {
            Map<String, Object> response = userService.updateUser(currentUsername, userInfo);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
