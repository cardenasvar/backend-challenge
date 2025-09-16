package com.challenge.backend.exception;

/**
 * ServicioExternoException
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
public class ServicioExternoException extends RuntimeException {

    public ServicioExternoException(String message) {
        super(message);
    }

    public  ServicioExternoException(String message, Throwable cause) {
        super(message, cause);
    }

}
