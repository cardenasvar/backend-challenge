package com.challenge.backend.controller.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * PorcentajeResponseDto
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@AllArgsConstructor
@Data
@Builder
public class PorcentajeResponseDto {

    private final String id;

    private final Integer value;

    private final Timestamp createdAt;

}
