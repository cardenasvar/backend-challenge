package com.challenge.backend.service;

import com.challenge.backend.service.domain.HistorialSolicitud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * HistorialSolicitudService.
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
public interface HistorialSolicitudService {

    void registrar(String endpoint, String parametros, String respuesta, String error);

    Page<HistorialSolicitud> obtener(Pageable pageable);

}
