package com.java.eventtracker.Auth.controller;


import com.java.eventtracker.Auth.model.AuthRequest;
import com.java.eventtracker.Auth.service.AuthenticationServiceImpl;
import com.java.eventtracker.email.dto.ForgotPasswordRequest;
import com.java.eventtracker.email.dto.ResetPasswordRequest;
import com.java.eventtracker.email.service.PasswordResetService;
import com.java.eventtracker.users.model.User;
import com.java.eventtracker.users.service.UserService;
import com.java.eventtracker.users.service.UserServiceImpl;
import com.java.eventtracker.utils.RestHelper;
import com.java.eventtracker.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationServiceImpl loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetService passwordResetService;
    /**
     * Handles the authentication for the user provided credentials.
     *
     * @param authRequest The authentication credentials containing object
     * @return The access keys and refresh keys for the associated authenticated user.
     */
    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@RequestBody AuthRequest authRequest) {
        Map<String, Object> listHashMap = new HashMap<>(loginService.authenticate(authRequest));
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
        Map<String, Object> tokenMap = new HashMap<>(loginService.refreshToken(refreshToken));
        return RestHelper.responseSuccess(tokenMap);
    }

    /**
     * Signing up the new user.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Sends the password reset link to the concerned email.
     *
     * @param request The password request body containing the email of the user whose password is to be reset.
     * @return The confirmation that the password reset link has been sent.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<RestResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String message = passwordResetService.forgotPassword(request);
        return RestHelper.responseMessage(message);
    }

    /**
     * Resets the password from the provided token and the password.
     *
     * @param resetPasswordRequest The reset password request containing the jwt token and the password.
     * @return The message indicating that the password has been reset successfully.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<RestResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String message = passwordResetService.resetPassword(resetPasswordRequest);
        return RestHelper.responseMessage(message);
    }
}
