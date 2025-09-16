package com.challenge.backend.repository;

import com.challenge.backend.repository.datastore.HistorialSolicitudJpaRepository;
import com.challenge.backend.repository.datastore.mapper.HistorialSolicitudModelMapper;
import com.challenge.backend.service.domain.HistorialSolicitud;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * HistorialSolicitudRepositoryImpl
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@Repository
@RequiredArgsConstructor
public class HistorialSolicitudRepositoryImpl implements HistorialSolicitudRepository {

    private final HistorialSolicitudJpaRepository historialSolicitudJpaRepository;
    private final HistorialSolicitudModelMapper historialSolicitudModelMapper;

    @Override
    public HistorialSolicitud guardar(HistorialSolicitud historialSolicitud) {
        var historialModel = historialSolicitudModelMapper.toHistorialSolicitudModel(historialSolicitud);
        return historialSolicitudModelMapper.toHistorialSolicitud(historialSolicitudJpaRepository.save(historialModel));
    }

    @Override
    public Page<HistorialSolicitud> buscarTodo(Pageable pageable) {
        return historialSolicitudJpaRepository.findAll(pageable)
                .map(historialSolicitudModelMapper::toHistorialSolicitud);
    }

}
