package com.challenge.backend.repository;

import com.challenge.backend.service.domain.HistorialSolicitud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * HistorialSolicitudRepository
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
public interface HistorialSolicitudRepository {

    HistorialSolicitud guardar(HistorialSolicitud historialSolicitud);

    Page<HistorialSolicitud> buscarTodo(Pageable pageable);

}
