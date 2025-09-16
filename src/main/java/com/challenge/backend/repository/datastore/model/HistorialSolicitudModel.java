package com.challenge.backend.repository.datastore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HistorialSolicitudModel
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "historial_solicitud")
public class HistorialSolicitudModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historial_solicitud_key_gen")
    @SequenceGenerator(name = "historial_solicitud_key_gen", sequenceName = "historial_solicitud_seq",
            allocationSize = 1)
    private Long id;

    private LocalDateTime timestamp;

    private String endpoint;
    private String parametros;
    private String respuesta;
    private String error;

}
