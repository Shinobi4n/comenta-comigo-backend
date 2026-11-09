package com.comentacomigo.comenta_comigo_backend.repository;

import com.comentacomigo.comenta_comigo_backend.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObraRepository extends JpaRepository<Obra, Long> {
}