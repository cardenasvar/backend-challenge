package com.challenge.backend.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CalculoRequestDto
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CalculoRequestDto {

    @NotNull(message = "El campo 'num1' es requerido y no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El campo 'num1' debe ser mayor que 0")
    private BigDecimal num1;

    @NotNull(message = "El campo 'num2' es requerido y no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El campo 'num2' debe ser mayor que 0")
    private BigDecimal num2;

    @Override
    public String toString() {
        return String.format("{\"num1\":%s, \"num2\":%s}", num1, num2);
    }

}
