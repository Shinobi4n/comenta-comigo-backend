package com.comentacomigo.comenta_comigo_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comentacomigo.comenta_comigo_backend.model.Obra;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {
}