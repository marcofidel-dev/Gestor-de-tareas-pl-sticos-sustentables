package com.gestortareas.model.dto;

/**
 * Respuesta del login: el token JWT y sus metadatos.
 */
public class LoginResponse {

    private final String token;
    private final String tipo = "Bearer";
    private final long expiraEnMs;

    public LoginResponse(String token, long expiraEnMs) {
        this.token = token;
        this.expiraEnMs = expiraEnMs;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public long getExpiraEnMs() {
        return expiraEnMs;
    }
}
