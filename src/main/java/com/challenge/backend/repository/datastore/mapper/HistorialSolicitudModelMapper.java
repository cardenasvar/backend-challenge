package com.challenge.backend.repository.datastore.mapper;

import com.challenge.backend.repository.datastore.model.HistorialSolicitudModel;
import com.challenge.backend.service.domain.HistorialSolicitud;
import org.mapstruct.Mapper;

/**
 * HistorialSolicitudModelMapper
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@Mapper(componentModel = "spring")
public interface HistorialSolicitudModelMapper {

    HistorialSolicitudModel toHistorialSolicitudModel(HistorialSolicitud historialSolicitud);

    HistorialSolicitud toHistorialSolicitud(HistorialSolicitudModel historialSolicitudModel);

}
