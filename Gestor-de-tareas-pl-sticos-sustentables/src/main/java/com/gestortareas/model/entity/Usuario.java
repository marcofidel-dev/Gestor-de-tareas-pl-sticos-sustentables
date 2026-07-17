package com.gestortareas.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Usuario del sistema. Cada usuario tiene un {@link Rol} (muchos usuarios por rol).
 * Tabla: {@code usuarios}.
 *
 * <p>La contrasena se persiste SIEMPRE hasheada (campo {@code contrasenaHash});
 * el hasheo corresponde a la capa de servicio, no a la entidad.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    /** Hash de la contrasena (p. ej. BCrypt). Nunca texto plano. */
    @Column(name = "contrasena_hash", nullable = false)
    private String contrasenaHash;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    protected Usuario() {
    }

    public Usuario(Rol rol, String nombreCompleto, String correo, String contrasenaHash) {
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}

