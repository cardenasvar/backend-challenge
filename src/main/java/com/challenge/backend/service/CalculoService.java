package com.challenge.backend.service;

import com.challenge.backend.controller.dto.CalculoResponseDto;
import java.math.BigDecimal;

/**
 * CalculoService
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
public interface CalculoService {

    CalculoResponseDto calcular(BigDecimal num1, BigDecimal num2);

}
