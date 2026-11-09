package com.comentacomigo.comenta_comigo_backend.dto;

import com.comentacomigo.comenta_comigo_backend.model.TipoObra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ObraRequestDTO(
        @NotBlank(message = "O título não pode estar em branco") String titulo,

        @NotNull(message = "O tipo da obra é obrigatório") TipoObra tipo,

        Integer anoLancamento,

        String sinopse,

        String capaUrl) {
}