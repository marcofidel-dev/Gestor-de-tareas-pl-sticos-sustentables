package com.gestortareas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gestortareas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCorreo(String correo);

    // Trae el rol en la misma consulta: el UserDetailsService lo necesita fuera de la sesion JPA.
    @EntityGraph(attributePaths = "rol")
    Optional<Usuario> findByCorreo(String correo);
}
