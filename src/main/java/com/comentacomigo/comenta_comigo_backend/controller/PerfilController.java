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

import com.comentacomigo.comenta_comigo_backend.dto.PerfilAtualizacaoDTO;
import com.comentacomigo.comenta_comigo_backend.dto.PerfilRequestDTO;
import com.comentacomigo.comenta_comigo_backend.dto.PerfilResponseDTO;
import com.comentacomigo.comenta_comigo_backend.service.PerfilService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) {
        this.service = service;
    }

    @GetMapping
    public List<PerfilResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PerfilResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public PerfilResponseDTO buscarPorUsuario(@PathVariable Long usuarioId) {
        return service.buscarPorUsuario(usuarioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PerfilResponseDTO criar(@Valid @RequestBody PerfilRequestDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public PerfilResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody PerfilAtualizacaoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}