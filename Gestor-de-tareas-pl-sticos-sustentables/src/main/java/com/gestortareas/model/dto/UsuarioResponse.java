package com.gestortareas.model.dto;

import com.gestortareas.model.entity.Usuario;

/**
 * Representacion segura de un usuario para devolver al cliente.
 * NUNCA expone la contrasena (ni siquiera su hash).
 */
public class UsuarioResponse {

    private final Long id;
    private final String nombreCompleto;
    private final String correo;
    private final String rol;
    private final boolean activo;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nombreCompleto = usuario.getNombreCompleto();
        this.correo = usuario.getCorreo();
        this.rol = usuario.getRol().getNombreRol();
        this.activo = Boolean.TRUE.equals(usuario.getActivo());
    }

    public Long getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }
}
