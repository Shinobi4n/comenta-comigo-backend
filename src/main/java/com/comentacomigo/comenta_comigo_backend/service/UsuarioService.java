package com.comentacomigo.comenta_comigo_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return buscarEntidade(id);
    }

    public Usuario criar(Usuario usuario) {

        if (repository.existsByEmail(usuario.getEmail())) {
            throw emailJaCadastrado();
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return repository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dados) {

        Usuario usuario = buscarEntidade(id);

        if (!usuario.getEmail().equals(dados.getEmail())
                && repository.existsByEmail(dados.getEmail())) {
            throw emailJaCadastrado();
        }

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());

        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        }

        return repository.save(usuario);
    }

    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Usuario buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    private ResponseStatusException emailJaCadastrado() {
        return new ResponseStatusException(
                HttpStatus.CONFLICT, "E-mail já cadastrado");
    }
}
