package com.gestortareas.exception;

/** Se lanza cuando la contrasena y su confirmacion no coinciden. -> HTTP 400. */
public class ContrasenasNoCoincidenException extends RuntimeException {

    public ContrasenasNoCoincidenException(String mensaje) {
        super(mensaje);
    }
}
