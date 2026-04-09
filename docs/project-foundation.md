# Base funcional del proyecto

## 1. Vision del producto

Desarrollar un SSG para el control interno de los computadores utilizados por campers de Campuslands en Cajasan, permitiendo registrar inventario, asignaciones, devoluciones, novedades e indicadores operativos.

## 2. Rol Product Owner

### Levantamiento de requerimientos

Preguntas clave para reuniones con cliente:

1. Que tipos de equipos se prestan y que datos deben registrarse de cada uno.
2. Quien autoriza una asignacion o una devolucion.
3. Que restricciones existen por salon, clan, jornada o sede.
4. Que incidentes deben reportarse y en cuanto tiempo deben resolverse.
5. Que reportes necesita coordinacion academica y soporte.

### Comprension de la problematica

- Hoy el control puede dispersarse en hojas de calculo, mensajes o validaciones manuales.
- Eso genera perdida de trazabilidad, demoras en soporte y dificultad para identificar responsables.
- El sistema debe disminuir errores y mejorar auditoria interna.

### Posibles soluciones

- Aplicativo web interno con autenticacion por roles.
- Dashboard operativo con resumen de disponibilidad y alertas.
- Flujo de prestamo y devolucion con validaciones de negocio.
- Registro historico de incidencias con severidad y estado.

## 3. Abstraccion de requerimientos

### Tipo de negocio

Rubro: tecnologia y formacion intensiva tipo bootcamp.

### Flujo del negocio

1. El camper solicita o recibe un equipo.
2. El staff valida disponibilidad.
3. Se asigna el computador y queda evidencia.
4. Durante el uso pueden reportarse incidencias.
5. El equipo se devuelve, valida y cambia de estado.
6. Coordinacion consulta indicadores y trazabilidad.

## 4. Documentacion de requerimientos

### Historias de usuario

- Como coordinador quiero registrar campers para asociarles equipos correctamente.
- Como soporte quiero registrar computadores con serial y placa para controlar inventario.
- Como staff quiero asignar un computador a un camper para saber quien usa cada equipo.
- Como staff quiero registrar devoluciones para liberar equipos disponibles.
- Como soporte quiero registrar incidencias para priorizar mantenimientos.
- Como coordinador quiero ver un dashboard para tomar decisiones sobre disponibilidad y riesgos.

### Product backlog inicial

| ID | Historia | Prioridad |
|---|---|---|
| HU-01 | Registrar campers | Alta |
| HU-02 | Registrar computadores | Alta |
| HU-03 | Asignar computador | Alta |
| HU-04 | Registrar devolucion | Alta |
| HU-05 | Reportar incidencia | Alta |
| HU-06 | Consultar dashboard | Media |
| HU-07 | Gestionar autenticacion y roles | Media |
| HU-08 | Exportar reportes | Baja |

### Kanban sugerido

- `Backlog`
- `Por hacer`
- `En progreso`
- `En revision`
- `En pruebas`
- `Hecho`

### Sprint 1 sugerido

Objetivo: tener el flujo minimo operativo del sistema.

- HU-01 Registrar campers
- HU-02 Registrar computadores
- HU-03 Asignar computador
- HU-04 Registrar devolucion
- HU-05 Reportar incidencia
- HU-06 Consultar dashboard

## 5. Frontend

### Wireframe de baja fidelidad

- Header con nombre del sistema y acceso rapido.
- Tarjetas KPI para equipos disponibles, asignados e incidencias.
- Tabla de computadores.
- Tabla de prestamos activos.
- Formulario rapido de incidencia.

### Mockup de media fidelidad

- Panel administrativo con menu lateral.
- Colores institucionales sobrios.
- Prioridad visual a estados del inventario.

### Prototipo de alta fidelidad

- Navegacion por modulos.
- Formularios con validacion.
- Estados visuales de alerta para incidencias criticas.

### Maquetado

Opciones recomendadas:

- Spring Boot + Thymeleaf para una primera entrega institucional.
- React o Vue si se requiere SPA mas adelante.

## 6. Backend

### UML de clases principal

- `Camper`: id, documento, nombreCompleto, correo, clan, activo
- `Computador`: id, serial, placaInventario, marca, modelo, ubicacion, estado
- `PrestamoComputador`: id, camper, computador, fechaAsignacion, fechaDevolucionProgramada, fechaEntregaReal, estado, observaciones
- `Incidencia`: id, computador, prestamo, fechaReporte, tipo, severidad, estado, descripcion, reportadoPor

### Diagrama de secuencia resumido

1. Usuario registra prestamo.
2. Controlador recibe solicitud.
3. Servicio valida camper y computador.
4. Servicio valida reglas de negocio.
5. Repositorio persiste prestamo.
6. Sistema actualiza estado del computador.

### Casos de uso

- Registrar camper
- Registrar computador
- Asignar computador
- Registrar devolucion
- Reportar incidencia
- Consultar dashboard

### Herramienta sugerida

Visual Paradigm para modelado UML formal del proyecto.

## 7. Base de datos

### DDL requerido

- Tablas: `campers`, `computadores`, `prestamos_computador`, `incidencias`
- Indices por documento, correo, serial y placa inventario
- Llaves foraneas entre prestamos, campers y computadores

### DML

- Inserts iniciales de campers y computadores
- Actualizaciones de estado segun prestamo o incidencia

### DQL

- Consultas de disponibilidad
- Consultas de prestamos activos
- Consultas de incidencias abiertas o criticas

### Seguridad

- Roles sugeridos: `ADMIN`, `COORDINACION`, `SOPORTE`
- Auditoria de fecha de creacion, actualizacion y usuario responsable en una siguiente iteracion

### Procedimientos, funciones, disparadores y eventos

- Procedimiento para asignar equipos con reglas de disponibilidad
- Trigger para marcar equipo como asignado al crear prestamo activo
- Trigger para liberar equipo al finalizar prestamo
- Evento para detectar prestamos vencidos y cambiarlos a atrasado

## 8. Arquitectura recomendada

- `controller`: entrada HTTP
- `service`: reglas de negocio
- `repository`: acceso a datos
- `model`: entidades del dominio
- `dto`: contratos de entrada y salida
- `exception`: manejo centralizado de errores

## 9. Siguiente fase recomendada

1. Agregar autenticacion y autorizacion por roles.
2. Integrar MySQL para ambiente real.
3. Crear vistas administrativas completas.
4. Implementar pruebas unitarias e integracion.
5. Agregar auditoria y exportacion de reportes.
