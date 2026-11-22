package com.comentacomigo.comenta_comigo_backend.service;

import com.comentacomigo.comenta_comigo_backend.dto.ComentarioDTO;
import com.comentacomigo.comenta_comigo_backend.model.Comentario;
import com.comentacomigo.comenta_comigo_backend.model.Obra;
import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.ComentarioRepository;
import com.comentacomigo.comenta_comigo_backend.repository.ObraRepository;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ObraRepository obraRepository;
    private final UsuarioRepository usuarioRepository;

    public ComentarioService(
            ComentarioRepository comentarioRepository,
            ObraRepository obraRepository,
            UsuarioRepository usuarioRepository) {

        this.comentarioRepository = comentarioRepository;
        this.obraRepository = obraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Comentario criarComentario(ComentarioDTO dto) {

        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Obra não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Comentario comentario = new Comentario();

        comentario.setTexto(dto.getTexto());
        comentario.setDataCriacao(LocalDateTime.now());
        comentario.setObra(obra);
        comentario.setUsuario(usuario);

        return comentarioRepository.save(comentario);
    }

    public List<Comentario> listarComentarios(Long obraId) {
        return comentarioRepository.findByObraId(obraId);
    }

    public void deletarComentario(Long id) {

        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comentário não encontrado"));

        comentarioRepository.delete(comentario);
    }
}
