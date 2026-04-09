# SSG Control Campers

Sistema base en Spring Boot para el control interno de computadores usados por campers de Campuslands en las instalaciones de Cajasan.

## Objetivo

Centralizar el inventario, la asignacion de equipos, las devoluciones y el registro de incidencias para tener trazabilidad operativa y apoyo a la toma de decisiones.

## Modulos iniciales

- Gestion de campers
- Gestion de computadores
- Prestamos y devoluciones
- Registro de incidencias
- Dashboard de resumen operativo

## Tecnologias

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Thymeleaf
- H2 para desarrollo
- MySQL listo para despliegue posterior

## Ejecucion

```bash
./mvnw spring-boot:run
```

La app inicia en `http://localhost:8080` y la consola H2 queda disponible en `http://localhost:8080/h2-console`.

## Endpoints

- `GET /api/campers`
- `POST /api/campers`
- `GET /api/computadores`
- `POST /api/computadores`
- `GET /api/prestamos`
- `POST /api/prestamos`
- `PATCH /api/prestamos/{id}/devolucion`
- `GET /api/incidencias`
- `POST /api/incidencias`
- `GET /api/dashboard/resumen`

## Ejemplo de prestamo

```json
{
  "camperId": 1,
  "computadorId": 2,
  "fechaAsignacion": "2026-04-09",
  "fechaDevolucionProgramada": "2026-04-16",
  "observaciones": "Equipo asignado para modulo de Java"
}
```

## Documentacion de analisis

- [Base funcional del proyecto](docs/project-foundation.md)
- [Script SQL inicial](sql/ssg_control_campers.sql)
# SSG_Control_Campers
