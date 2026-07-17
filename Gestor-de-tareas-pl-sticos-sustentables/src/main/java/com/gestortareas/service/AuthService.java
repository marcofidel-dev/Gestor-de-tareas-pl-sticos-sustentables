package com.gestortareas.service;

import com.gestortareas.model.dto.LoginRequest;
import com.gestortareas.model.dto.LoginResponse;
import com.gestortareas.model.dto.RegistroRequest;
import com.gestortareas.model.dto.UsuarioResponse;

public interface AuthService {

    /**
     * Registra un nuevo usuario.
     *
     * @throws com.gestortareas.exception.ContrasenasNoCoincidenException si la contrasena y su confirmacion no coinciden
     * @throws com.gestortareas.exception.CorreoYaRegistradoException      si el correo ya esta en uso
     */
    UsuarioResponse registrar(RegistroRequest request);

    /**
     * Autentica al usuario y devuelve un token JWT.
     *
     * @throws org.springframework.security.core.AuthenticationException si las credenciales son invalidas
     */
    LoginResponse iniciarSesion(LoginRequest request);
}
