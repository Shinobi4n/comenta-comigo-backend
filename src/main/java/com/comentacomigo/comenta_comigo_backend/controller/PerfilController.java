
package com.comentacomigo.comenta_comigo_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.comentacomigo.comenta_comigo_backend.model.Perfil;
import com.comentacomigo.comenta_comigo_backend.service.PerfilService;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) {
        this.service = service;
    }

    @GetMapping
    public List<Perfil> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Perfil buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Perfil buscarPorUsuario(@PathVariable Long usuarioId) {
        return service.buscarPorUsuario(usuarioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Perfil criar(@RequestBody Perfil perfil) {
        return service.criar(perfil);
    }

    @PutMapping("/{id}")
    public Perfil atualizar(@PathVariable Long id, @RequestBody Perfil perfil) {
        return service.atualizar(id, perfil);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
