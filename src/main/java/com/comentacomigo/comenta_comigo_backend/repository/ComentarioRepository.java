
package com.comentacomigo.comenta_comigo_backend.repository;

import com.comentacomigo.comenta_comigo_backend.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByObraId(Long obraId);

}
