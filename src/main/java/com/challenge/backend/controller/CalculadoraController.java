package com.challenge.backend.controller;

import com.challenge.backend.controller.dto.CalculoRequestDto;
import com.challenge.backend.controller.dto.CalculoResponseDto;
import com.challenge.backend.service.CalculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CalculadoraController
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@RestController
@RequestMapping("calculadora")
@RequiredArgsConstructor
@Validated
public class CalculadoraController {

    private final CalculoService calculoService;

    /**
     * Recibe dos números, los suma y aplica un porcentaje obtenido de un servicio externo.
     *
     * @param calculoRequestDto CalculoRequestDto con num1 y num2
     * @return CalculoResponseDto resultado del cálculo
     */
    @Operation(
            description = "Recibe dos números, los suma y aplica un porcentaje obtenido de un servicio externo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cálculo exitoso"),
                    @ApiResponse(responseCode = "400",
                            description = "Ocurrió un error de cliente, verifique su request"),
                    @ApiResponse(responseCode = "500", description = "Ocurrió un error interno al realizar cálculo")
            }
    )
    @PostMapping("/calculo-porcentaje")
    public ResponseEntity<CalculoResponseDto> calcularPorcentaje(
            @Valid @RequestBody CalculoRequestDto calculoRequestDto) {
        var response = calculoService.calcular(calculoRequestDto.getNum1(), calculoRequestDto.getNum2());
        return ResponseEntity.ok(response);
    }

}
