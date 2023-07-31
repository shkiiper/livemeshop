package com.liveme.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.liveme.config.UserInfoUserDetailsService;
import com.liveme.service.JwtService;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String accessToken = null;
        String refreshToken = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
            username = jwtService.extractUsername(accessToken);
        } else if (authHeader != null && authHeader.startsWith("Refresh ")) {
            refreshToken = authHeader.substring(8);
            username = jwtService.extractUsername(refreshToken);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (accessToken != null && jwtService.validateToken(accessToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else if (refreshToken != null && jwtService.validateToken(refreshToken, userDetails)) {
                String newAccessToken = jwtService.generateToken(username); // Создаем новый Access Token
                response.setHeader("Authorization", "Bearer " + newAccessToken); // Устанавливаем новый Access Token в
                                                                                 // заголовке ответа
            }
        }
        filterChain.doFilter(request, response);
    }
}
