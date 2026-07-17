package com.gestortareas.exception;

/** Se lanza al intentar registrar un correo que ya existe. -> HTTP 409. */
public class CorreoYaRegistradoException extends RuntimeException {

    public CorreoYaRegistradoException(String mensaje) {
        super(mensaje);
    }
}
