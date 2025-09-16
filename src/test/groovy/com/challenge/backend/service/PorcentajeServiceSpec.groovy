package com.challenge.backend.service

import com.challenge.backend.controller.dto.PorcentajeResponseDto
import com.challenge.backend.exception.ServicioExternoException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Unroll

class PorcentajeServiceSpec extends Specification {

    private RestTemplate restTemplate
    private RedisTemplate<String, BigDecimal> redisTemplate
    ValueOperations<String, BigDecimal> valueOperations = Mock()

    private PorcentajeService porcentajeService

    def setup() {
        restTemplate = Mock()
        redisTemplate = Mock()
        porcentajeService = new PorcentajeServiceImpl(restTemplate, redisTemplate)
        redisTemplate.opsForValue() >> valueOperations
    }

    @Unroll
    def "obtenerPorcentaje - cuando el servicio externo responde OK"() {
        given: "Porcentaje"
        def responseList = List.of(PorcentajeResponseDto.builder().value(15).build())
        restTemplate.exchange(_,_,_,_) >> new ResponseEntity(responseList, HttpStatus.OK)

        when: "Se invoca al método obtener porcentaje"
        def resultado = porcentajeService.obtenerPorcentaje()

        then: "La respuesta es OK y el resultado correcto"
        resultado == 15
    }

    @Unroll
    def "obtenerPorcentaje - usar caché cuando servicio externo falla"() {
        given: "El servicio externo falla pero hay valor en cache"
        restTemplate.exchange(_,_,_,_) >> { throw new RuntimeException("Servicio no disponible") }

        when: "Se invoca al método obtener porcentaje"
        def resultado = porcentajeService.obtenerPorcentaje()

        then: "La respuesta es OK y el resultado correcto"
        valueOperations.get("porcentajeCache") >> BigDecimal.valueOf(12)

        and: "Se retorna el valor del caché"
        resultado == 12
    }

    @Unroll
    def "obtenerPorcentaje - usar caché cuando servicio externo devuelve lista vacía"() {
        given: "El servicio externo devuelve lista vacía"
        restTemplate.exchange(_,_,_,_) >> new ResponseEntity([], HttpStatus.OK)

        when: "Se invoca al método obtener porcentaje"
        def resultado = porcentajeService.obtenerPorcentaje()

        then: "Se consulta la caché"
        valueOperations.get("porcentajeCache") >> BigDecimal.valueOf(8)

        and: "Se retorna el valor del caché"
        resultado == 8
    }

    @Unroll
    def "obtenerPorcentaje - usar caché cuando servicio externo devuelve una respuesta con body null"() {
        given: "El servicio externo devuelve respuesta sin body"
        restTemplate.exchange(_,_,_,_) >> new ResponseEntity(null, HttpStatus.OK)

        when: "Se invoca al método obtener porcentaje"
        def resultado = porcentajeService.obtenerPorcentaje()

        then: "Se consulta la caché"
        valueOperations.get("porcentajeCache") >> BigDecimal.valueOf(8)

        and: "Se retorna el valor del caché"
        resultado == 8
    }

    @Unroll
    def "obtenerPorcentaje - excepcion servicio externo falla y no existe valor en caché"() {
        given: "El servicio externo devuelve lista vacía"
        restTemplate.exchange(_,_,_,_) >> new ResponseEntity([], HttpStatus.OK)

        when: "Se invoca al método obtener porcentaje"
        porcentajeService.obtenerPorcentaje()

        then: "Se consulta la caché"
        valueOperations.get("porcentajeCache") >> null

        and: "Se retorna el valor del caché"
        ServicioExternoException exception = thrown()
        exception.message == "Servicio externo no disponible y no existe valor en cache"
    }

    @Unroll
    def "obtenerPorcentaje - excepcion guardando valor en caché"() {
        given: "El servicio externo responde correctamente"
        def responseList = List.of(PorcentajeResponseDto.builder().value(30).build())
        restTemplate.exchange(_,_,_,_) >> new ResponseEntity(responseList, HttpStatus.OK)

        when: "Se invoca al método obtener porcentaje"
        def resultado = porcentajeService.obtenerPorcentaje()

        then: "Ocurre error al guardar en caché"
        valueOperations.set(_,_,_) >> { throw new RuntimeException("Redis timeout") }

        and: "Se retorna el valor del caché"
        resultado == 30
    }

    @Unroll
    def "obtenerPorcentaje - excepcion obteniendo valor en caché"() {
        given: "El servicio externo responde una lista vacía"
        restTemplate.exchange(_,_,_,_) >> new ResponseEntity([], HttpStatus.OK)

        when: "Se invoca al método obtener porcentaje"
        porcentajeService.obtenerPorcentaje()

        then: "Ocurre error al obtener en caché"
        valueOperations.get("porcentajeCache") >> { throw new RuntimeException("Redis timeout") }

        and: "Se retorna excepción"
        ServicioExternoException exception = thrown()
        exception.message == "Servicio externo no disponible y no existe valor en cache"
    }

}
