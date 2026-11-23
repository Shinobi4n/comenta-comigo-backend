package com.comentacomigo.comenta_comigo_backend.repository;

import java.util.Optional;
import com.comentacomigo.comenta_comigo_backend.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    Optional<Sessao> findByToken(String token);
}
