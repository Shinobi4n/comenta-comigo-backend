package com.comentacomigo.comenta_comigo_backend.dto;

import com.comentacomigo.comenta_comigo_backend.model.TipoObra;

public record ObraResponseDTO(
    Long id,
    String titulo,
    TipoObra tipo,
    Integer anoLancamento,
    String sinopse,
    String capaUrl
) {
}