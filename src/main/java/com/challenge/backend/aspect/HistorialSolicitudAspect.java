package com.challenge.backend.aspect;

import com.challenge.backend.service.HistorialSolicitudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HistorialSolicitudAspect
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class HistorialSolicitudAspect {

    private final HistorialSolicitudService historialSolicitudService;
    private final ObjectMapper objectMapper;

    @AfterReturning(
            pointcut = "execution(* com.challenge.backend.controller.CalculadoraController.calcularPorcentaje(..))",
            returning = "resultado")
    public void afterRespuestaExitosa(JoinPoint joinPoint, Object resultado) {
        historialSolicitudService.registrar(
                obtenerEndpointActual(),
                obtenerParametrosSolicitud(joinPoint),
                formatearRespuesta(resultado),
                null);
    }

    @AfterThrowing(
            pointcut = "execution(* com.challenge.backend.controller.CalculadoraController.calcularPorcentaje(..))",
            throwing = "exception")
    public void afterRespuestaFallida(JoinPoint joinPoint, Exception exception) {
        historialSolicitudService.registrar(
                obtenerEndpointActual(),
                obtenerParametrosSolicitud(joinPoint),
                null,
                exception.getMessage());
    }

    private String obtenerEndpointActual() {
        var attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest().getRequestURI();
    }

    private String formatearRespuesta(Object resultado) {
        if (resultado == null) {
            return null;
        }

        try {
            Object body = resultado;
            if (resultado instanceof ResponseEntity<?> responseEntity) {
                body = responseEntity.getBody();
            }

            if (body != null) {
                return objectMapper.writeValueAsString(body);
            }
            return "{}";
        } catch (JsonProcessingException e) {
            log.warn("Error al formatear respuesta: {}", e.getMessage());
            return resultado.toString();
        }
    }

    private String obtenerParametrosSolicitud(JoinPoint joinPoint) {
        StringBuilder params = new StringBuilder();
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg != null && !isHttpObject(arg)) {
                params.append(arg);
            }
        }
        return !params.isEmpty() ? params.toString() : "";
    }

    private boolean isHttpObject(Object obj) {
        return obj instanceof HttpServletRequest
                || obj instanceof HttpServletResponse;
    }

}
