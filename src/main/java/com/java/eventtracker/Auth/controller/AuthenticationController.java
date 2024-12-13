package com.java.eventtracker.Auth.controller;


import com.java.eventtracker.Auth.model.AuthRequest;
import com.java.eventtracker.Auth.service.AuthenticationService;
import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.service.UsersService;
import com.java.eventtracker.utils.RestHelper;
import com.java.eventtracker.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService loginService;

    @Autowired
    private UsersService usersService;
    /**
     * Handles the authentication for the user provided credentials.
     *
     * @param authRequest The authentication credentials containing object
     * @return The access keys and refresh keys for the associated authenticated user.
     */
    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@RequestBody AuthRequest authRequest) {
        HashMap<String, Object> listHashMap = new HashMap<>(loginService.authenticate(authRequest));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Handles token refresh using a valid refresh token
     *
     * @param authorizationHeader Headers with Authorization keyword
     * @return New access and refresh tokens
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<RestResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Extract token from Bearer authorization header
        String refreshToken = authorizationHeader.substring(7); // Remove "Bearer "
        HashMap<String, Object> tokenMap = new HashMap<>(loginService.refreshToken(refreshToken));
        return RestHelper.responseSuccess(tokenMap);
    }

    /**
     * Signing up the new user.
     *
     * @param users The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody Users users) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", usersService.save(users));
        return RestHelper.responseSuccess(listHashMap);
    }
}
