package com.comentacomigo.comenta_comigo_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comentacomigo.comenta_comigo_backend.model.TokenRecuperacao;

public interface TokenRecuperacaoRepository extends JpaRepository<TokenRecuperacao, Long> {

    Optional<TokenRecuperacao> findByToken(String token);

    void deleteByUsuarioId(Long usuarioId);
}
