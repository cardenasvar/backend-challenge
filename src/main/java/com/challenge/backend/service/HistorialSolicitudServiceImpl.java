package com.challenge.backend.service;

import com.challenge.backend.repository.HistorialSolicitudRepository;
import com.challenge.backend.service.domain.HistorialSolicitud;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * HistorialSolicitudServiceImpl
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HistorialSolicitudServiceImpl implements HistorialSolicitudService {

    private final HistorialSolicitudRepository historialSolicitudRepository;

    @Async
    @Override
    public void registrar(String endpoint, String parametros, String respuesta, String error) {
        log.info("Registrando historial de solicitud");
        try {
            HistorialSolicitud historialSolicitud = HistorialSolicitud.builder()
                    .timestamp(LocalDateTime.now())
                    .endpoint(endpoint)
                    .parametros(parametros)
                    .respuesta(respuesta)
                    .error(error)
                    .build();
            historialSolicitudRepository.guardar(historialSolicitud);
        } catch (Exception e) {
            log.warn("Error registrando historial de solicitud: {}", e.getMessage());
        }
    }

    @Override
    public Page<HistorialSolicitud> obtener(Pageable pageable) {
        return historialSolicitudRepository.buscarTodo(pageable);
    }

}
