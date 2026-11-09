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

import com.comentacomigo.comenta_comigo_backend.dto.ObraRequestDTO;
import com.comentacomigo.comenta_comigo_backend.dto.ObraResponseDTO;
import com.comentacomigo.comenta_comigo_backend.service.ObraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/obras")
public class ObraController {

    private final ObraService service;

    public ObraController(ObraService service) {
        this.service = service;
    }

    @GetMapping
    public List<ObraResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ObraResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ObraResponseDTO criar(@Valid @RequestBody ObraRequestDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public ObraResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ObraRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}