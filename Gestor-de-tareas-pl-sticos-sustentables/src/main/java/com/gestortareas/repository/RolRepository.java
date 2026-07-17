package com.gestortareas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestortareas.model.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombreRol(String nombreRol);
}
