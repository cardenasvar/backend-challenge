package com.challenge.backend.aspect;

import com.challenge.backend.service.HistorialSolicitudService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
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

    @AfterReturning(
            pointcut = "execution(* com.challenge.backend.controller.CalculadoraController.calcularPorcentaje(..))",
            returning = "resultado")
    public void afterRespuestaExitosa(JoinPoint joinPoint, Object resultado) {
        historialSolicitudService.registrar(
                obtenerEndpointActual(),
                obtenerParametrosSolicitud(joinPoint),
                resultado != null ? resultado.toString() : null,
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

    private String obtenerParametrosSolicitud(JoinPoint joinPoint) {
        StringBuilder params = new StringBuilder();
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg != null && !isHttpObject(arg)) {
                params.append(arg).append(";");
            }
        }
        return !params.isEmpty() ? params.toString() : "";
    }

    private boolean isHttpObject(Object obj) {
        return obj instanceof HttpServletRequest
                || obj instanceof HttpServletResponse;
    }

}
