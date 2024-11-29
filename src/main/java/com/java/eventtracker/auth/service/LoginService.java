package com.java.eventtracker.auth.service;

import com.java.eventtracker.auth.helper.JwtService;
import com.java.eventtracker.auth.helper.UserInfoService;
import com.java.eventtracker.auth.model.AuthRequest;
import com.java.eventtracker.utils.exception.GlobalExceptionWrapper;
import lombok.NonNull;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private UserInfoService service;

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
    public String authenticate(@NonNull AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new GlobalExceptionWrapper.BadRequestException("Invalid Credentials.");
        }
    }


}
