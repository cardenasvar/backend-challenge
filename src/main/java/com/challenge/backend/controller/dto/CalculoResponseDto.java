package com.challenge.backend.controller.dto;

import lombok.Builder;
import java.math.BigDecimal;

/**
 * CalculoResponseDto
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@Builder
public record CalculoResponseDto(BigDecimal result) {

}
