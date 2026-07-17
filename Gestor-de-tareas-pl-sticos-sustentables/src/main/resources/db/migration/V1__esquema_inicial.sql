-- V1: Esquema inicial del gestor de tareas.
-- Los tipos coinciden con los que Hibernate espera para las entidades JPA
-- (por eso 'activo' es BIT(1) y las fechas con hora son DATETIME(6)),
-- de modo que 'spring.jpa.hibernate.ddl-auto=validate' pase sin errores.

CREATE TABLE roles (
    id_rol       BIGINT       NOT NULL AUTO_INCREMENT,
    nombre_rol   VARCHAR(50)  NOT NULL,
    descripcion  VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id_rol),
    CONSTRAINT uk_roles_nombre UNIQUE (nombre_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
    id_usuario       BIGINT       NOT NULL AUTO_INCREMENT,
    nombre_completo  VARCHAR(150) NOT NULL,
    correo           VARCHAR(150) NOT NULL,
    contrasena_hash  VARCHAR(255) NOT NULL,
    activo           BIT(1)       NOT NULL,
    id_rol           BIGINT       NOT NULL,
    PRIMARY KEY (id_usuario),
    CONSTRAINT uk_usuarios_correo UNIQUE (correo),
    CONSTRAINT fk_usuarios_rol FOREIGN KEY (id_rol) REFERENCES roles (id_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tareas (
    id_tarea      BIGINT        NOT NULL AUTO_INCREMENT,
    titulo        VARCHAR(200)  NOT NULL,
    descripcion   VARCHAR(1000) DEFAULT NULL,
    fecha_limite  DATE          DEFAULT NULL,
    prioridad     VARCHAR(20)   DEFAULT NULL,
    estado        VARCHAR(30)   NOT NULL,
    id_creador    BIGINT        NOT NULL,
    PRIMARY KEY (id_tarea),
    CONSTRAINT fk_tareas_creador FOREIGN KEY (id_creador) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE asignaciones (
    id_asignacion     BIGINT      NOT NULL AUTO_INCREMENT,
    id_tarea          BIGINT      NOT NULL,
    id_usuario        BIGINT      NOT NULL,
    fecha_asignacion  DATETIME(6) NOT NULL,
    PRIMARY KEY (id_asignacion),
    CONSTRAINT fk_asignaciones_tarea   FOREIGN KEY (id_tarea)   REFERENCES tareas (id_tarea),
    CONSTRAINT fk_asignaciones_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE historial_tareas (
    id_historial      BIGINT        NOT NULL AUTO_INCREMENT,
    campo_modificado  VARCHAR(100)  NOT NULL,
    valor_anterior    VARCHAR(1000) DEFAULT NULL,
    valor_nuevo       VARCHAR(1000) DEFAULT NULL,
    fecha_cambio      DATETIME(6)   NOT NULL,
    id_tarea          BIGINT        NOT NULL,
    id_usuario        BIGINT        NOT NULL,
    PRIMARY KEY (id_historial),
    CONSTRAINT fk_historial_tarea   FOREIGN KEY (id_tarea)   REFERENCES tareas (id_tarea),
    CONSTRAINT fk_historial_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
