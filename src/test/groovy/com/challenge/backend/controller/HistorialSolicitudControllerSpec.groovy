package com.challenge.backend.controller

import com.challenge.backend.service.HistorialSolicitudService
import com.challenge.backend.service.domain.HistorialSolicitud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

class HistorialSolicitudControllerSpec extends Specification {

    private HistorialSolicitudService historialSolicitudService
    private HistorialSolicitudController historialSolicitudController

    def setup() {
        historialSolicitudService = Mock()
        historialSolicitudController = new HistorialSolicitudController(historialSolicitudService)
    }

    @Unroll
    def "obtenerHistorialSolicitudes - retorna página con historial"() {
        given: "Un pagina de historial de solicitud"
        Pageable pageable = PageRequest.of(0, 5, Sort.by("timestamp").descending())
        def historialSolicitudesList = List.of(
                HistorialSolicitud.builder().id(1).build(),
                HistorialSolicitud.builder().id(2).build()
        )

        Page<HistorialSolicitud> historialSolicitudPage = new PageImpl<>(historialSolicitudesList, pageable, 1)
        historialSolicitudService.obtener(_) >> historialSolicitudPage

        when: "Se invoca al obtener historial solicitudes"
        def response = historialSolicitudController.obtenerHistorialSolicitudes(page, size, sortBy, sortDirection)

        then: "La respuesta con la página del historial de solicitudes"
        response.statusCode == HttpStatus.OK
        response.getBody().content.size() == 2
        response.getBody().totalElements == 2

        where: "Datos"
        page | size | sortBy      | sortDirection
        0    | 10   | "timestamp" | "asc"
        1    | 10   | "id"        | "desc"
    }

    @Unroll
    def "obtenerHistorialSolicitudes - retorna página vacía"() {
        given: "Un pagina de historial de solicitud"
        Pageable pageable = PageRequest.of(0, 5, Sort.by("timestamp").descending())
        Page<HistorialSolicitud> historialSolicitudEmptyPage = Page.empty(pageable)
        historialSolicitudService.obtener(_) >> historialSolicitudEmptyPage

        when: "Se invoca al obtener historial solicitudes"
        def response = historialSolicitudController.obtenerHistorialSolicitudes(page, size, sortBy, sortDirection)

        then: "La respuesta con la página del historial de solicitudes vacía"
        response.statusCode == HttpStatus.OK
        response.getBody().content.isEmpty()
        response.getBody().totalElements == 0

        where: "Datos"
        page | size | sortBy      | sortDirection
        0    | 10   | "timestamp" | "asc"
    }

}
