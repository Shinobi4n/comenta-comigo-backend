package com.comentacomigo.comenta_comigo_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comentacomigo.comenta_comigo_backend.dto.PerfilAtualizacaoDTO;
import com.comentacomigo.comenta_comigo_backend.dto.PerfilRequestDTO;
import com.comentacomigo.comenta_comigo_backend.dto.PerfilResponseDTO;
import com.comentacomigo.comenta_comigo_backend.model.Perfil;
import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.PerfilRepository;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilService(PerfilRepository perfilRepository, UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PerfilResponseDTO> listar() {
        return perfilRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PerfilResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public PerfilResponseDTO buscarPorUsuario(Long usuarioId) {
        return perfilRepository.findByUsuarioId(usuarioId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado"));
    }

    public PerfilResponseDTO criar(PerfilRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (perfilRepository.existsByUsuarioId(dto.usuarioId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuário já possui perfil");
        }

        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        perfil.setBio(dto.bio());
        perfil.setFotoUrl(dto.fotoUrl());
        return toResponse(perfilRepository.save(perfil));
    }

    public PerfilResponseDTO atualizar(Long id, PerfilAtualizacaoDTO dto) {

        Perfil perfil = buscarEntidade(id);
        perfil.setBio(dto.bio());
        perfil.setFotoUrl(dto.fotoUrl());
        return toResponse(perfilRepository.save(perfil));
    }

    public void excluir(Long id) {
        perfilRepository.delete(buscarEntidade(id));

    }

    private Perfil buscarEntidade(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado"));
    }

    private PerfilResponseDTO toResponse(Perfil perfil) {

        return new PerfilResponseDTO(
                perfil.getId(),
                perfil.getBio(),
                perfil.getFotoUrl(),
                perfil.getUsuario().getId(),
                perfil.getUsuario().getNome());
    }

}
