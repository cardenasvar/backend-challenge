package com.challenge.backend.service;

import com.challenge.backend.controller.dto.PorcentajeResponseDto;
import com.challenge.backend.exception.ServicioExternoException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * PorcentajeServiceImpl
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@Service
@Slf4j
public class PorcentajeServiceImpl implements PorcentajeService {

    @Value("${app.external-service.porcentaje-url}")
    private String externalServiceUrl;

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, BigDecimal> redisTemplate;

    public PorcentajeServiceImpl(RestTemplate restTemplate,
                                 RedisTemplate<String, BigDecimal> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    public BigDecimal obtenerPorcentaje() {
        Optional<BigDecimal> porcentajeEnServicioExterno = obtenerPorcentajeServicioExterno();
        if (porcentajeEnServicioExterno.isPresent()) {
            return porcentajeEnServicioExterno.get();
        }

        Optional<BigDecimal> porcentajeEnCache = obtenerPorcentajeEnCache();
        if (porcentajeEnCache.isPresent()) {
            return porcentajeEnCache.get();
        }

        throw new ServicioExternoException("Servicio externo no disponible y no existe valor en cache");
    }

    private Optional<BigDecimal> obtenerPorcentajeServicioExterno() {
        try {
            log.info("Obteniendo porcentaje desde servicio externo");
            ResponseEntity<List<PorcentajeResponseDto>> responseEntity = restTemplate.exchange(
                    externalServiceUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            if (responseEntity.getBody() == null || responseEntity.getBody().isEmpty()) {
                log.warn("Servicio externo sin respuesta");
                return Optional.empty();
            }

            BigDecimal porcentaje = BigDecimal.valueOf(responseEntity.getBody().getFirst().getValue());
            log.info("Porcentaje obtenido en servicio externo: {}", porcentaje);
            guardarPorcentajeEnCache(porcentaje);
            return Optional.of(porcentaje);
        } catch (Exception e) {
            log.warn("Error obteniendo porcentaje desde servicio externo: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private void guardarPorcentajeEnCache(BigDecimal porcentaje) {
        try {
            redisTemplate.opsForValue().set("porcentajeCache", porcentaje, Duration.ofMinutes(30));
            log.info("Porcentaje guardado en cache: {}", porcentaje);
        } catch (Exception e) {
            log.warn("Error guardando porcentaje en cache: {}", e.getMessage());
        }
    }

    private Optional<BigDecimal> obtenerPorcentajeEnCache() {
        try {
            log.info("Obteniendo porcentaje desde cache");
            BigDecimal porcentaje = redisTemplate.opsForValue().get("porcentajeCache");
            log.info("Porcentaje obtenido en cache: {}", porcentaje);
            return Optional.ofNullable(porcentaje);
        } catch (Exception e) {
            log.warn("Error leyendo cache: {}", e.getMessage());
            return Optional.empty();
        }
    }

}
