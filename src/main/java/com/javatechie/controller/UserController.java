package com.javatechie.controller;

import com.javatechie.dto.AuthRequest;
import com.javatechie.entity.UserInfo;
import com.javatechie.service.JwtService;
import com.javatechie.service.UserService;
import org.springframework.security.core.AuthenticationException;

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

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    // @GetMapping("/all")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    // public List<User> getAllTheProducts() {
    // return service.getUser();
    // }
    //
    // @GetMapping("/{id}")
    // @PreAuthorize("hasAuthority('ROLE_USER')")
    // public Product getProductById(@PathVariable int id) {
    // return service.getProduct(id);
    // }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(),
                        authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getName());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }

}
