package com.challenge.backend.controller;

import com.challenge.backend.service.HistorialSolicitudService;
import com.challenge.backend.service.domain.HistorialSolicitud;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * HistorialSolicitudController
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@RestController
@RequestMapping("historial-solicitud")
@RequiredArgsConstructor
public class HistorialSolicitudController {

    private final HistorialSolicitudService historialSolicitudService;

    @Operation(
            description = "Obtener historial de solicitudes",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Respuesta exitosa al obtener historial de solicitudes"),
                    @ApiResponse(responseCode = "204", description = "Sin datos de historial de solicitudes"),
                    @ApiResponse(responseCode = "400", description = "Ocurrió un error de cliente, verifique su request"),
                    @ApiResponse(responseCode = "500",
                            description = "Ocurrió un error interno al obtener el historial de solicitudes")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<HistorialSolicitud>> obtenerHistorialSolicitudes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<HistorialSolicitud> historialSolicitudPage = historialSolicitudService.obtener(pageable);
        return ResponseEntity.ok(historialSolicitudPage);
    }

}
