package com.comentacomigo.comenta_comigo_backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.AvaliacaoRepository;
import com.comentacomigo.comenta_comigo_backend.repository.ComentarioRepository;
import com.comentacomigo.comenta_comigo_backend.repository.SessaoRepository;
import com.comentacomigo.comenta_comigo_backend.repository.TokenRecuperacaoRepository;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final SessaoRepository sessaoRepository;
    private final TokenRecuperacaoRepository tokenRecuperacaoRepository;
    private final ComentarioRepository comentarioRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repository, SessaoRepository sessaoRepository,
            TokenRecuperacaoRepository tokenRecuperacaoRepository,
            ComentarioRepository comentarioRepository,
            AvaliacaoRepository avaliacaoRepository) {
        this.repository = repository;
        this.sessaoRepository = sessaoRepository;
        this.tokenRecuperacaoRepository = tokenRecuperacaoRepository;
        this.comentarioRepository = comentarioRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return buscarEntidade(id);
    }

    public Usuario criar(Usuario usuario) {

        if (usuario.getNome() == null || usuario.getNome().isBlank()
                || usuario.getEmail() == null || usuario.getEmail().isBlank()
                || usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe nome, e-mail e senha");
        }

        if (repository.existsByEmail(usuario.getEmail())) {
            throw emailJaCadastrado();
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return repository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dados) {

        Usuario usuario = buscarEntidade(id);

        if (dados.getNome() == null || dados.getNome().isBlank() || dados.getEmail() == null
                || dados.getEmail().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe nome e e-mail");
        }

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

    @Transactional
    public void excluir(Long id) {

        Usuario usuario = buscarEntidade(id);

        sessaoRepository.deleteByUsuarioId(id);
        tokenRecuperacaoRepository.deleteByUsuarioId(id);
        comentarioRepository.deleteByUsuarioId(id);
        avaliacaoRepository.deleteByUsuarioId(id);

        repository.delete(usuario);

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
