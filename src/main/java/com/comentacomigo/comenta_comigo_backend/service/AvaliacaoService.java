
package com.comentacomigo.comenta_comigo_backend.service;

import com.comentacomigo.comenta_comigo_backend.dto.ComentarioDTO;
import com.comentacomigo.comenta_comigo_backend.model.Avaliacao;
import com.comentacomigo.comenta_comigo_backend.model.Obra;
import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.AvaliacaoRepository;
import com.comentacomigo.comenta_comigo_backend.repository.ObraRepository;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ObraRepository obraRepository;
    private final UsuarioRepository usuarioRepository;

    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            ObraRepository obraRepository,
            UsuarioRepository usuarioRepository) {

        this.avaliacaoRepository = avaliacaoRepository;
        this.obraRepository = obraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Avaliacao criarAvaliacao(ComentarioDTO dto) {

        if (dto.getNota() == null || dto.getNota() < 1 || dto.getNota() > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A nota deve ser entre 1 e 5."
            );
        }

        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Obra não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setNota(dto.getNota());
        avaliacao.setObra(obra);
        avaliacao.setUsuario(usuario);

        return avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> listarAvaliacoes(Long obraId) {
        return avaliacaoRepository.findByObraId(obraId);
    }

    public void deletarAvaliacao(Long id) {

        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Avaliação não encontrada"));

        avaliacaoRepository.delete(avaliacao);
    }
}
