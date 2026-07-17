package com.gestortareas.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestortareas.model.entity.Usuario;
import com.gestortareas.repository.UsuarioRepository;

/**
 * Carga un usuario desde la BD (por correo) para que Spring Security lo autentique.
 * El rol se expone como autoridad con el prefijo 'ROLE_' (convencion de Spring).
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales invalidas"));

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasenaHash())
                .authorities(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol()))
                .disabled(!Boolean.TRUE.equals(usuario.getActivo()))
                .build();
    }
}
