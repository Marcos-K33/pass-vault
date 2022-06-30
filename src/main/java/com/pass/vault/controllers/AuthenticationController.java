package com.pass.vault.controllers;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pass.vault.config.JwtTokenUtil;
import com.pass.vault.entities.UserEntity;
import com.pass.vault.requests.LoginRequest;
import com.pass.vault.requests.RegisterRequest;
import com.pass.vault.responses.LoginResponse;
import com.pass.vault.services.UserService;

@RestController
@RequestMapping("/auth/")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenUtil jwtTUtil;

    @Autowired
    UserService uService;

    @PostMapping(path = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            Authentication auth = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserEntity user = (UserEntity) auth.getPrincipal();
            String token = jwtTUtil.generateAccesToken(user);
            LoginResponse response = new LoginResponse(user.getEmail(), token);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(path = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        try {
            UserEntity savedEntity = uService.registerUser(request);
            if (savedEntity != null) {
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyMap());
    }

}
