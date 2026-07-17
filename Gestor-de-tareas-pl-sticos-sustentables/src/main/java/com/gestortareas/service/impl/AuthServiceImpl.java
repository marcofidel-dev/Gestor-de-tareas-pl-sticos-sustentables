package com.gestortareas.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestortareas.exception.ContrasenasNoCoincidenException;
import com.gestortareas.exception.CorreoYaRegistradoException;
import com.gestortareas.model.dto.LoginRequest;
import com.gestortareas.model.dto.LoginResponse;
import com.gestortareas.model.dto.RegistroRequest;
import com.gestortareas.model.dto.UsuarioResponse;
import com.gestortareas.model.entity.Rol;
import com.gestortareas.model.entity.Usuario;
import com.gestortareas.repository.RolRepository;
import com.gestortareas.repository.UsuarioRepository;
import com.gestortareas.security.JwtService;
import com.gestortareas.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    /** Rol asignado por defecto a quien se auto-registra. */
    private static final String ROL_POR_DEFECTO = "ADMINISTRADOR";

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           RolRepository rolRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public UsuarioResponse registrar(RegistroRequest request) {
        // 1) La contrasena y su confirmacion deben coincidir.
        if (!request.getContrasena().equals(request.getConfirmacionContrasena())) {
            throw new ContrasenasNoCoincidenException(
                    "La contrasena y su confirmacion no coinciden");
        }

        // 2) El correo debe ser unico. Se normaliza a minusculas.
        String correo = request.getCorreo().trim().toLowerCase();
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new CorreoYaRegistradoException(
                    "El correo ya esta registrado: " + correo);
        }

        // 3) Rol por defecto (debe existir; lo siembra DataInitializer al arrancar).
        Rol rol = rolRepository.findByNombreRol(ROL_POR_DEFECTO)
                .orElseThrow(() -> new IllegalStateException(
                        "El rol '" + ROL_POR_DEFECTO + "' no existe. Verifique la carga inicial de roles."));

        // 4) Se guarda SIEMPRE el hash de la contrasena, nunca el texto plano.
        String hash = passwordEncoder.encode(request.getContrasena());

        Usuario usuario = new Usuario(rol, request.getNombreCompleto().trim(), correo, hash);
        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario);
    }

    @Override
    public LoginResponse iniciarSesion(LoginRequest request) {
        String correo = request.getCorreo().trim().toLowerCase();

        // Valida credenciales; lanza AuthenticationException si son incorrectas.
        Authentication autenticacion = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(correo, request.getContrasena()));

        UserDetails userDetails = (UserDetails) autenticacion.getPrincipal();
        String token = jwtService.generarToken(userDetails);

        return new LoginResponse(token, jwtService.getExpirationMs());
    }
}
