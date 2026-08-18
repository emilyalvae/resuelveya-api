package com.resuelveya.resuelve_api.business.api.dto.resenia;

import java.time.LocalDateTime;

public record ReseniaResponseDto(
        Long id,
        Integer calificacion,
        String comentario,
        Long clienteId,
        String clienteNombre,
        String clienteFotoUrl,
        Long tecnicoId,
        LocalDateTime createdAt
) {
}
