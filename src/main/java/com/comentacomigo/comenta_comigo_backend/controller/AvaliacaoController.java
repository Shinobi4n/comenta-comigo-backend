
package com.comentacomigo.comenta_comigo_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.comentacomigo.comenta_comigo_backend.dto.ComentarioDTO;
import com.comentacomigo.comenta_comigo_backend.model.Avaliacao;
import com.comentacomigo.comenta_comigo_backend.service.AvaliacaoService;

@RestController
@RequestMapping
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @PostMapping("/obras/{obraId}/avaliacoes")
    @ResponseStatus(HttpStatus.CREATED)
    public Avaliacao criar(
            @PathVariable Long obraId,
            @RequestBody ComentarioDTO dto) {

        dto.setObraId(obraId);
        return service.criarAvaliacao(dto);
    }

    @GetMapping("/obras/{obraId}/avaliacoes")
    public List<Avaliacao> listar(@PathVariable Long obraId) {
        return service.listarAvaliacoes(obraId);
    }

    @DeleteMapping("/avaliacoes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.deletarAvaliacao(id);
    }
}
