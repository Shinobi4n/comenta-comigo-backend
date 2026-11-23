package com.comentacomigo.comenta_comigo_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.UUID;
import java.time.LocalDateTime;
import com.comentacomigo.comenta_comigo_backend.model.Sessao;
import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.SessaoRepository;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(
            UsuarioRepository usuarioRepository,
            SessaoRepository sessaoRepository

    ) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
    }

    public Sessao login(Usuario dados) {

        if (dados.getEmail() == null || dados.getEmail().isBlank() || dados.getSenha() == null
                || dados.getSenha().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe e-mail e senha");
        }

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(dados.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos"));

        if (!passwordEncoder.matches(dados.getSenha(), usuario.getSenha())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos");
        }

        Sessao sessao = new Sessao();

        sessao.setUsuario(usuario);
        sessao.setToken(UUID.randomUUID().toString());
        sessao.setCriadaEm(LocalDateTime.now());
        sessao.setExpiraEm(LocalDateTime.now().plusHours(24));

        return sessaoRepository.save(sessao);

    }

    public void logout(String token) {

        Sessao sessao = sessaoRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Sessão inválida"));

        sessaoRepository.delete(sessao);

    }

}
