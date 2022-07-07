package com.pass.vault.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.pass.vault.utils.ResponseWrapper;

@RestController
@RequestMapping("/auth/")
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenUtil jwtTUtil;

    @Autowired
    UserService uService;

    @PostMapping(path = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> login(@RequestBody @Valid LoginRequest request) {
        log.info("**** Inicio servicio de Login ****");
        ResponseWrapper rw = new ResponseWrapper();
        try {
            log.info("Datos recibidos {}", request);
            Authentication auth = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserEntity user = (UserEntity) auth.getPrincipal();
            String token = jwtTUtil.generateAccesToken(user);
            LoginResponse response = new LoginResponse(user.getEmail(), token);
            rw.setMessage("Access ok");
            rw.setSuccess(true);
            rw.setStatus(HttpStatus.OK);
            rw.setData(response);
            log.info("**** fin servicio de login ****");
            return ResponseEntity.ok(rw);
        } catch (BadCredentialsException e) {
            log.error("Ocurrió un errror al intentar el login", e);
            rw.unauthenticated();
            return ResponseEntity.status(rw.getStatus()).body(rw);
        }
    }

    @PostMapping(path = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> register(@RequestBody @Valid RegisterRequest request) {
        log.info("**** Inicio servicio de registro *****");
        ResponseWrapper rw = new ResponseWrapper();
        try {
            log.info("Datos recibidos {}", request);
            UserEntity savedEntity = uService.registerUser(request);
            if (savedEntity.getId() != null && savedEntity.getId() > 0) {
                rw.OK();
                log.info("**** fin servicio registro ****");
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al hacer el registro del usuario", e);
            rw.errorServer();
        }
        return ResponseEntity.status(rw.getStatus()).body(rw);
    }

}
