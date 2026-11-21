
package com.comentacomigo.comenta_comigo_backend.repository;

import com.comentacomigo.comenta_comigo_backend.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByObraId(Long obraId);

}
