
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
import com.comentacomigo.comenta_comigo_backend.model.Comentario;
import com.comentacomigo.comenta_comigo_backend.service.ComentarioService;

@RestController
@RequestMapping
public class ComentarioController {

    private final ComentarioService service;

    public ComentarioController(ComentarioService service) {
        this.service = service;
    }

    @PostMapping("/obras/{obraId}/comentarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Comentario criar(
            @PathVariable Long obraId,
            @RequestBody ComentarioDTO dto) {

        dto.setObraId(obraId);
        return service.criarComentario(dto);
    }

    @GetMapping("/obras/{obraId}/comentarios")
    public List<Comentario> listar(@PathVariable Long obraId) {
        return service.listarComentarios(obraId);
    }

    @DeleteMapping("/comentarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.deletarComentario(id);
    }
}

