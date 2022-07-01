package com.pass.vault.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pass.vault.requests.VaultPassRequest;
import com.pass.vault.services.VaultPassService;

@RestController
@RequestMapping("/pass/")
public class PassController {

    @Autowired
    VaultPassService vPService;

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saludo(@RequestBody @Valid VaultPassRequest request,
            @RequestHeader("Authorization") String token) {
        if (!token.startsWith("Bearer "))
            return ResponseEntity.badRequest().build();

        return (vPService.savePassword(request, token))
                ? ResponseEntity.status(200).body("Se ha guardado la contraseña de manera correcta")
                : ResponseEntity.status(408).body("Ha ocurrido un error al guardar la contraseña");
    }
}
