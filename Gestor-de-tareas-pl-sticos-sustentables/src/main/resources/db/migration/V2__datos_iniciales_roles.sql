-- V2: Roles base del sistema (antes los sembraba DataInitializer, ahora viven aqui).

INSERT INTO roles (nombre_rol, descripcion) VALUES
    ('ADMINISTRADOR', 'Gestiona las tareas de sus colaboradores'),
    ('COLABORADOR',   'Empleado que ejecuta las tareas asignadas');
