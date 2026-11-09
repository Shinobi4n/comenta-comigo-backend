package com.comentacomigo.comenta_comigo_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comentacomigo.comenta_comigo_backend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
}