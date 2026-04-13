CREATE DATABASE IF NOT EXISTS ssg_control_campers;
USE ssg_control_campers;

CREATE TABLE campers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    documento VARCHAR(20) NOT NULL UNIQUE,
    nombre_completo VARCHAR(120) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    clan VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE computadores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    serial VARCHAR(50) NOT NULL UNIQUE,
    placa_inventario VARCHAR(30) NOT NULL UNIQUE,
    marca VARCHAR(60) NOT NULL,
    modelo VARCHAR(60) NOT NULL,
    ubicacion VARCHAR(80) NOT NULL,
    estado VARCHAR(20) NOT NULL
);

CREATE TABLE prestamos_computador (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    camper_id BIGINT NOT NULL,
    computador_id BIGINT NOT NULL,
    fecha_asignacion DATE NOT NULL,
    fecha_devolucion_programada DATE NOT NULL,
    fecha_entrega_real DATE NULL,
    estado VARCHAR(20) NOT NULL,
    observaciones VARCHAR(250),
    CONSTRAINT fk_prestamo_camper FOREIGN KEY (camper_id) REFERENCES campers(id),
    CONSTRAINT fk_prestamo_computador FOREIGN KEY (computador_id) REFERENCES computadores(id)
);

CREATE TABLE incidencias (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    computador_id BIGINT NOT NULL,
    prestamo_id BIGINT NULL,
    fecha_reporte DATE NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    severidad VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    reportado_por VARCHAR(100) NOT NULL,
    CONSTRAINT fk_incidencia_computador FOREIGN KEY (computador_id) REFERENCES computadores(id),
    CONSTRAINT fk_incidencia_prestamo FOREIGN KEY (prestamo_id) REFERENCES prestamos_computador(id)
);

CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(120) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO campers (documento, nombre_completo, correo, clan, activo) VALUES
('109000001', 'Laura Martinez', 'laura.martinez@campuslands.com', 'Cajasan-Backend', TRUE),
('109000002', 'Nicolas Rojas', 'nicolas.rojas@campuslands.com', 'Cajasan-Frontend', TRUE);

INSERT INTO computadores (serial, placa_inventario, marca, modelo, ubicacion, estado) VALUES
('SN-CAJ-1001', 'INV-001', 'Lenovo', 'ThinkPad E14', 'Sala 1', 'DISPONIBLE'),
('SN-CAJ-1002', 'INV-002', 'HP', 'ProBook 440', 'Sala 2', 'DISPONIBLE');

INSERT INTO usuarios (nombre_completo, correo, password, rol, activo) VALUES
('Administrador Campus', 'admin@campuslands.com', '$2y$10$sks5ad3wZBH9MCMZLO.sIuwu0zwhNt6/vOmV4U/u88igDrTBUaoV2', 'ADMIN', TRUE),
('Coordinacion Campus', 'coordinacion@campuslands.com', '$2y$10$sks5ad3wZBH9MCMZLO.sIuwu0zwhNt6/vOmV4U/u88igDrTBUaoV2', 'COORDINACION', TRUE),
('Soporte Campus', 'soporte@campuslands.com', '$2y$10$sks5ad3wZBH9MCMZLO.sIuwu0zwhNt6/vOmV4U/u88igDrTBUaoV2', 'SOPORTE', TRUE);

DELIMITER //

CREATE PROCEDURE sp_asignar_computador(
    IN p_camper_id BIGINT,
    IN p_computador_id BIGINT,
    IN p_fecha_asignacion DATE,
    IN p_fecha_devolucion DATE,
    IN p_observaciones VARCHAR(250)
)
BEGIN
    INSERT INTO prestamos_computador (
        camper_id,
        computador_id,
        fecha_asignacion,
        fecha_devolucion_programada,
        estado,
        observaciones
    ) VALUES (
        p_camper_id,
        p_computador_id,
        p_fecha_asignacion,
        p_fecha_devolucion,
        'ACTIVO',
        p_observaciones
    );
END //

CREATE TRIGGER trg_prestamo_activo_insert
AFTER INSERT ON prestamos_computador
FOR EACH ROW
BEGIN
    IF NEW.estado = 'ACTIVO' THEN
        UPDATE computadores
        SET estado = 'ASIGNADO'
        WHERE id = NEW.computador_id;
    END IF;
END //

CREATE TRIGGER trg_prestamo_cierre_update
AFTER UPDATE ON prestamos_computador
FOR EACH ROW
BEGIN
    IF NEW.estado IN ('FINALIZADO', 'ATRASADO', 'CANCELADO') THEN
        UPDATE computadores
        SET estado = 'DISPONIBLE'
        WHERE id = NEW.computador_id;
    END IF;
END //

DELIMITER ;

CREATE VIEW vw_dashboard_operativo AS
SELECT
    (SELECT COUNT(*) FROM campers WHERE activo = TRUE) AS campers_activos,
    (SELECT COUNT(*) FROM computadores) AS total_computadores,
    (SELECT COUNT(*) FROM computadores WHERE estado = 'DISPONIBLE') AS disponibles,
    (SELECT COUNT(*) FROM computadores WHERE estado = 'ASIGNADO') AS asignados,
    (SELECT COUNT(*) FROM incidencias WHERE estado = 'ABIERTA') AS incidencias_abiertas;
