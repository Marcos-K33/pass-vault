package com.pass.vault.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pass.vault.entities.VaultPassEntity;
import com.pass.vault.requests.VaultPassRequest;
import com.pass.vault.responses.ShowPasswordResponse;
import com.pass.vault.services.VaultPassService;
import com.pass.vault.utils.ResponseWrapper;

@RestController
@RequestMapping("/pass/")
public class PassController {

    private final Logger log = LoggerFactory.getLogger(PassController.class);

    @Autowired
    VaultPassService vPService;

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> saludo(@RequestBody @Valid VaultPassRequest request,
            @RequestHeader("Authorization") String token) {
        log.info("**** Inicio servicio guardar contrasenia ****");
        ResponseWrapper rw = new ResponseWrapper();
        if (!token.startsWith("Bearer "))
            return ResponseEntity.badRequest().build();

        log.info("Datos recibidos {}", request);
        if (!vPService.savePassword(request, token)) {
            rw.errorServer();
            rw.setMessage("Ha ocurrido un error al guardar la contraseña");
        } else {
            rw.OK();
            rw.setStatus(HttpStatus.CREATED);
        }
        log.info("**** fin servicio guardar contrasenia ****");
        return ResponseEntity.status(rw.getStatus()).body(rw);
    }

    @PostMapping("getMyPasswords")
    public ResponseEntity<ResponseWrapper> getPassWords(@RequestHeader("Authorization") String token) {
        log.info("**** inicio servicio obtener contrasenias ****");
        ResponseWrapper rw = new ResponseWrapper();
        if (!token.startsWith("Bearer "))
            return ResponseEntity.badRequest().build();

        List<VaultPassEntity> passwords = vPService.getPasswords(token);
        if (!passwords.isEmpty()) {
            rw.OK();
            rw.setData(passwords);
        } else {
            rw.setData(Collections.emptyList());
            rw.setSuccess(true);
            rw.setStatus(HttpStatus.NO_CONTENT);
            rw.setMessage("No hay contraseñas guardadas aún");
        }
        log.info("**** fin servicio obtener contrasenias ****");
        return ResponseEntity.status(rw.getStatus()).body(rw);
    }

    @PostMapping(path = "showPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> showPassWord(@RequestBody Map<String, String> request) {
        log.info("**** inicio servicio mostrar contrasenia ****");
        ResponseWrapper rw = new ResponseWrapper();
        log.info("Datos recibidos {}", request);
        ShowPasswordResponse response = vPService.showPassWord(request);
        if (response.getPassword() != null && !response.getPassword().isEmpty()) {
            rw.OK();
            rw.setData(response);
        } else {
            rw.setData(Collections.emptyMap());
            rw.setMessage("No se ha podido desencriptar la contraseña");
            rw.setSuccess(false);
            rw.setStatus(HttpStatus.BAD_GATEWAY);
        }
        log.info("**** fin servicio mostrar contrasenia ****");
        return ResponseEntity.status(rw.getStatus()).body(rw);
    }

    @GetMapping("generate")
    public String generateRandomPassword(@RequestParam int leng) {
        return vPService.generatePasword(leng);
    }
}