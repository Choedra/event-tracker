package com.java.eventtracker.Auth.service;

import com.java.eventtracker.Auth.helper.JwtService;
import com.java.eventtracker.Auth.helper.UserInfoService;
import com.java.eventtracker.Auth.model.AuthRequest;
import com.java.eventtracker.utils.exception.GlobalExceptionWrapper;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class AuthenticationService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authenticates the user provided credentials.
     *
     * @param authRequest The user provided credentials.
     * @return The token on validating the user.
     */
    public HashMap<String, String> authenticate(@NonNull AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            HashMap<String, String> tokenMap = new HashMap<>();
            tokenMap.put("access_token", jwtService.generateToken(authRequest.getEmail()));
            tokenMap.put("refresh_token", jwtService.generateRefreshToken(authRequest.getEmail()));
            return tokenMap;
        } else {
            throw new GlobalExceptionWrapper.BadRequestException("Invalid Credentials.");
        }
    }

    public HashMap<String, String> refreshToken(String refreshToken) {
        // Check if token is a refresh token
        if (!isRefreshToken(refreshToken)) {
            throw new GlobalExceptionWrapper.BadRequestException("Invalid Refresh Token.");
        }

        // Extract username from the refresh token
        String username = jwtService.extractUsername(refreshToken);

        // Validate the refresh token
        UserDetails userDetails = userInfoService.loadUserByUsername(username);

        if (jwtService.validateToken(refreshToken, userDetails)) {
            HashMap<String, String> tokens = generateTokens(username);
            // omit refreshing of refresh tokens
            tokens.put("refreshToken", refreshToken);
            return tokens;
        } else {
            throw new GlobalExceptionWrapper.BadRequestException("Invalid or Expired Refresh Token.");
        }
    }

    /**
     * Additional method to check if the token is a refresh token
     *
     * @param token JWT token to check
     * @return boolean indicating if it's a refresh token
     */
    private boolean isRefreshToken(String token) {
        try {
            Claims claims = jwtService.extractAllClaims(token);
            // Check for a custom claim or another identifier for refresh tokens
            return claims.containsKey("type") && "refresh".equals(claims.get("type"));
        } catch (Exception e) {
            return false;
        }
    }

    private HashMap<String, String> generateTokens(String username) {
        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", jwtService.generateToken(username));
        tokenMap.put("refreshToken", jwtService.generateRefreshToken(username));
        return tokenMap;
    }
}
