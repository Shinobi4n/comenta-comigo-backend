package com.comentacomigo.comenta_comigo_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comentacomigo.comenta_comigo_backend.dto.UsuarioRequestDTO;
import com.comentacomigo.comenta_comigo_backend.dto.UsuarioResponseDTO;
import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw emailJaCadastrado();
        }
        Usuario usuario = new Usuario();
        preencher(usuario, dto);
        return toResponse(repository.save(usuario));
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarEntidade(id);
        if (!usuario.getEmail().equals(dto.email()) && repository.existsByEmail(dto.email())) {
            throw emailJaCadastrado();
        }
        preencher(usuario, dto);
        return toResponse(repository.save(usuario));
    }

    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Usuario buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    private ResponseStatusException emailJaCadastrado() {
        return new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
    }

    private void preencher(Usuario usuario, UsuarioRequestDTO dto) {
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
    }

    private UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}