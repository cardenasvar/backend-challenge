package com.challenge.backend.service;

import com.challenge.backend.controller.dto.CalculoResponseDto;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * CalculoServiceImpl
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@Service
@Slf4j
public class CalculoServiceImpl implements CalculoService {

    private final PorcentajeService porcentajeService;

    public CalculoServiceImpl(PorcentajeService porcentajeService) {
        this.porcentajeService = porcentajeService;
    }

    @Override
    public CalculoResponseDto calcular(BigDecimal num1, BigDecimal num2) {
        log.info("Calculando con numeros: {} y {}", num1, num2);
        BigDecimal suma = num1.add(num2);
        BigDecimal porcentaje = porcentajeService.obtenerPorcentaje();
        BigDecimal resultado = suma.add(suma.multiply(porcentaje.divide(BigDecimal.valueOf(100))));
        log.info("Resultado del calculo: {}", resultado);
        return CalculoResponseDto.builder()
                .result(resultado)
                .build();
    }

}
