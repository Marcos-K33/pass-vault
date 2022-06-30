package com.pass.vault.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pass/")
public class PassController {

    @PostMapping("saludo")
    public String saludo() {
        return "hola";
    }
}
