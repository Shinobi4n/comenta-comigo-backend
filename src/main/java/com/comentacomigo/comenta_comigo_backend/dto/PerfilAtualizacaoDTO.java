package com.comentacomigo.comenta_comigo_backend.dto;

import jakarta.validation.constraints.Size;

public record PerfilAtualizacaoDTO(

        @Size(max = 500, message = "A bio deve ter no máximo 500 caracteres") String bio,
        @Size(max = 300, message = "A URL da foto deve ter no máximo 300 caracteres") String fotoUrl) {

}
