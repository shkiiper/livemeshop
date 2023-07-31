package com.liveme.controller;

import com.liveme.dto.AuthRequest;
import com.liveme.entity.UserInfo;
import com.liveme.exception.BadRequestException;
import com.liveme.exception.InvalidTokenException;
import com.liveme.exception.SuccessException;
import com.liveme.service.JwtService;
import com.liveme.service.UserService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Missing or empty 'refreshToken' field.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String username = jwtService.extractUsername(refreshToken);
        if (username == null || username.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid refresh token.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String accessToken = jwtService.generateToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", newRefreshToken);

        return ResponseEntity.ok(response);
    }

    // @PostMapping("/login")
    // public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest
    // authRequest) {
    // try {

    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(authRequest.getName(),
    // authRequest.getPassword()));

    // if (authentication.isAuthenticated()) {
    // String username = authRequest.getName();
    // String accessToken = jwtService.generateToken(username);
    // String refreshToken = jwtService.generateRefreshToken(username);

    // Map<String, Object> response = new HashMap<>();
    // response.put("user", userService.getUserByName(username));
    // response.put("accessToken", accessToken);
    // response.put("refreshToken", refreshToken);

    // return ResponseEntity.ok(response);
    // } else {
    // throw new UsernameNotFoundException("Invalid user request!");
    // }
    // } catch (BadCredentialsException e) {
    // throw new UsernameNotFoundException("Invalid username or password");
    // }
    // }
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
            errorResponse.put("errorMessage", "Invalid password.");
            errorResponse.put("fieldName", "password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (UsernameNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "Ошибка");
            errorResponse.put("errorMessage", "Invalid username.");
            errorResponse.put("fieldName", "username");
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
    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userService.getUserByName(username);
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
