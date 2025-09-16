package com.challenge.backend.service.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HistorialSolicitud
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HistorialSolicitud {

    private Long id;

    private LocalDateTime timestamp;

    private String endpoint;
    private String parametros;
    private String respuesta;
    private String error;

}
