package com.challenge.backend.service

import com.challenge.backend.repository.HistorialSolicitudRepository
import com.challenge.backend.service.domain.HistorialSolicitud
import java.time.LocalDateTime
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.PageImpl
import spock.lang.Specification
import spock.lang.Unroll

class HistorialSolicitudServiceSpec extends Specification {

    private HistorialSolicitudRepository historialSolicitudRepository
    private HistorialSolicitudService historialSolicitudService

    def setup() {
        historialSolicitudRepository = Mock()
        historialSolicitudService = new HistorialSolicitudServiceImpl(historialSolicitudRepository)
    }

    @Unroll
    def "registrar historial de solicitud"() {
        given: "Datos de solicitud"
        def endpoint = "/api/porcentaje"
        def parametros = "tipo=normal"
        def respuesta = "{\"porcentaje\": 15.5}"
        def error = null

        when: "Se invoca al método registrar"
        historialSolicitudService.registrar(endpoint, parametros, respuesta, error)

        then: "Se guarda el historial en el repositorio"
        1 * historialSolicitudRepository.guardar(_ as HistorialSolicitud)

    }

    @Unroll
    def "registrar historial de solicitud - excepcion"() {
        given: "Datos de solicitud"
        def endpoint = "/api/porcentaje"
        def parametros = "tipo=normal"
        def respuesta = "{\"porcentaje\": 15.5}"
        def error = null

        historialSolicitudService.metaClass.log = [
                info: { String message -> },
                warn: { String message -> warnLogs << message }
        ]

        when: "Se invoca al método registrar"
        historialSolicitudService.registrar(endpoint, parametros, respuesta, error)

        and: "Se guarda el historial en el repositorio"
        historialSolicitudRepository.guardar(_) >> { throw new RuntimeException("Conexión a base de datos fallida") }

        and: "Warn"
        def warnLogs = []
        warnLogs.any { it.contains("Conexión a base de datos fallida") }

        then: "Lanza log warn y excepcion"
        noExceptionThrown()

    }

    @Unroll
    def "obtener pagina de historial de solicitudes"() {
        given: "una página de resultados del repositorio"
        def pageable = Pageable.ofSize(10).withPage(0)
        def historiales = [
                new HistorialSolicitud(
                        timestamp: LocalDateTime.now().minusHours(1),
                        endpoint: "/api/test1",
                        parametros: "param1=value1",
                        respuesta: "response1",
                        error: null
                ),
                new HistorialSolicitud(
                        timestamp: LocalDateTime.now().minusMinutes(30),
                        endpoint: "/api/test2",
                        parametros: "param2=value2",
                        respuesta: "response2",
                        error: "error2"
                )
        ]
        def expectedPage = new PageImpl<>(historiales, pageable, 2)

        when: "se solicita el historial paginado"
        def resultPage = historialSolicitudService.obtener(pageable)

        then: "se consulta al repositorio y se retorna la página"
        1 * historialSolicitudRepository.buscarTodo(pageable) >> expectedPage

        and: "se retorna la página correcta"
        resultPage == expectedPage

    }

}
