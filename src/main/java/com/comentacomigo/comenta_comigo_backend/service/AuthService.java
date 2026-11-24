package com.comentacomigo.comenta_comigo_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;
import com.comentacomigo.comenta_comigo_backend.model.Sessao;
import com.comentacomigo.comenta_comigo_backend.model.TokenRecuperacao;
import com.comentacomigo.comenta_comigo_backend.model.Usuario;
import com.comentacomigo.comenta_comigo_backend.repository.SessaoRepository;
import com.comentacomigo.comenta_comigo_backend.repository.TokenRecuperacaoRepository;
import com.comentacomigo.comenta_comigo_backend.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoRepository sessaoRepository;

    private final TokenRecuperacaoRepository tokenRecuperacaoRepository;
    private final EmailService emailService;

    @Value("${app.front-url:http://localhost:3000}")
    private String frontUrl;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(
            UsuarioRepository usuarioRepository,
            SessaoRepository sessaoRepository,
            TokenRecuperacaoRepository tokenRecuperacaoRepository,
            EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoRepository = sessaoRepository;
        this.tokenRecuperacaoRepository = tokenRecuperacaoRepository;
        this.emailService = emailService;
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

    public void solicitarRecuperacao(String email) {

        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe o e-mail");
        }

        Optional<Usuario> resultado = usuarioRepository.findByEmailIgnoreCase(email);

        if (resultado.isEmpty()) {
            return;
        }

        Usuario usuario = resultado.get();

        TokenRecuperacao tokenRecuperacao = new TokenRecuperacao();
        tokenRecuperacao.setUsuario(usuario);
        tokenRecuperacao.setToken(UUID.randomUUID().toString());
        tokenRecuperacao.setExpiraEm(LocalDateTime.now().plusMinutes(30));
        tokenRecuperacaoRepository.save(tokenRecuperacao);

        String link = frontUrl + "/redefinir-senha?token=" + tokenRecuperacao.getToken();

        emailService.recuperaSenha(usuario.getEmail(), link);
    }
}
