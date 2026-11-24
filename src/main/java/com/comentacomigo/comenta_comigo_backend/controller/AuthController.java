package com.comentacomigo.comenta_comigo_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("Authorization") String authorization) {

        String token = authorization.replace("Bearer ", "");

        service.logout(token);
    }

    @PostMapping("/recuperar-senha")
    public Map<String, String> recuperarSenha(@RequestBody Usuario dados) {

        service.solicitarRecuperacao(dados.getEmail());

        return Map.of("mensagem", "Se o e-mail estiver cadastrado, enviaremos as instruções de recuperação");
    }

}
