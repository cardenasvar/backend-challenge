package com.challenge.backend.controller

import com.challenge.backend.controller.dto.CalculoRequestDto
import com.challenge.backend.controller.dto.CalculoResponseDto
import com.challenge.backend.service.CalculoService
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

class CalculadoraControllerSpec extends Specification {

    private CalculoService calculoService
    private CalculadoraController calculadoraController

    def setup() {
        calculoService = Mock()
        calculadoraController = new CalculadoraController(calculoService)
    }

    @Unroll
    def "calcularPorcentaje - cuando request es válido retorna OK"() {
        given: "Es una request válida"
        def request = new CalculoRequestDto(new BigDecimal("100.0"), new BigDecimal("10.0"))
        def resultadoEsperado = new CalculoResponseDto(new BigDecimal("140.0"))
        calculoService.calcular(_,_) >> resultadoEsperado

        when: "Se invoca al método"
        def response = calculadoraController.calcularPorcentaje(request)

        then: "La respuesta es OK y el resultado correcto"
        response.statusCode == HttpStatus.OK
        response.getBody().result() == new BigDecimal("140.0")
    }

    @Unroll
    def "calcularPorcentaje - cuando request es null lanza NullPointerException"() {
        when: "Es una request null"
        calculadoraController.calcularPorcentaje(null)

        then: "Lanza NullPointerException"
        thrown(NullPointerException.class)
    }

}
