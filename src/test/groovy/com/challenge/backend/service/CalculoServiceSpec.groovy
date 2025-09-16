package com.challenge.backend.service

import spock.lang.Specification
import spock.lang.Unroll

class CalculoServiceSpec extends Specification {

    private CalculoService calculoService
    private PorcentajeService porcentajeService

    def setup() {
        porcentajeService = Mock()
        calculoService = new CalculoServiceImpl(porcentajeService)
    }

    @Unroll
    def "calcularPorcentaje - cuando request es válido retorna OK"() {
        given: "Porcentaje"
        porcentajeService.obtenerPorcentaje() >> new BigDecimal("10.0")

        when: "Se invoca al método"
        def response = calculoService.calcular(num1, num2)

        then: "La respuesta es OK y el resultado correcto"
        response.result() == new BigDecimal("165.0")

        where: "Datos"
        num1                     | num2
        new BigDecimal("100.0")  | new BigDecimal("50.0")
    }

}
