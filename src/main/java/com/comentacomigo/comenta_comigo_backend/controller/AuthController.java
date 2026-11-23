package com.comentacomigo.comenta_comigo_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.comentacomigo.comenta_comigo_backend.service.AuthService;
import com.comentacomigo.comenta_comigo_backend.model.Sessao;
import com.comentacomigo.comenta_comigo_backend.model.Usuario;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Usuario dados) {

        Sessao sessao = service.login(dados);

        return Map.of(
                "token", sessao.getToken(),
                "id", sessao.getUsuario().getId(),
                "nome", sessao.getUsuario().getNome(),
                "email", sessao.getUsuario().getEmail());

    }

}
