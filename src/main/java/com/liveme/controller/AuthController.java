package com.liveme.controller;

import com.liveme.dto.AuthRequest;
import com.liveme.service.JwtService;
import com.liveme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        try {
            // Perform user authentication using Spring Security's AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));

            // If authentication is successful, generate and return JWT tokens
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
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
