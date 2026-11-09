package com.comentacomigo.comenta_comigo_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comentacomigo.comenta_comigo_backend.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    boolean existsByUsuarioId(Long usuarioID);

    Optional<Perfil> findByUsuarioId(Long usuarioId);

}
