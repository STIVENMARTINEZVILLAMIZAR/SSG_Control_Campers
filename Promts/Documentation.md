# Documentacion de Prompts Usados con IA

## Proyecto

Sistema SSG para el control interno de computadores usados por campers de Campuslands en las instalaciones de Cajasan.

## Objetivo de este documento

Registrar los prompts principales utilizados durante la construccion del sistema con apoyo de inteligencia artificial, organizados por etapas del desarrollo.

## Prompt 1. Definicion inicial del sistema

```text
Requiero un experto con experiencia en Java proyectos reales para tener SSG de un control interno de los computadores que usan los campers de Campuslands en las instalaciones de Cajasan.

Seguir instrucciones: debe tener roles de levantamiento de requerimientos, documentacion, frontend, backend y base de datos.
```

### Resultado esperado

- Definicion del alcance funcional.
- Estructura base del proyecto.
- Vision del producto.
- Requerimientos de frontend, backend y database.

## Prompt 2. Construccion del backend base

```text
Construye una base profesional del proyecto en Spring Boot con entidades, servicios, controladores, repositorios y SQL inicial para campers, computadores, prestamos e incidencias.
```

### Resultado esperado

- Entidades JPA.
- Endpoints REST iniciales.
- Servicios con reglas de negocio.
- Script SQL para MySQL.

## Prompt 3. Analisis completo del proyecto

```text
Requiero que analices todo el proyecto.
```

### Resultado esperado

- Diagnostico de arquitectura.
- Hallazgos tecnicos.
- Riesgos y oportunidades de mejora.
- Prioridades siguientes del proyecto.

## Prompt 4. Mejora de la landing page

```text
Requiero que crees las secciones en la landing page. Faltan las secciones y colocar una de bienvenida y logo y lo otro login, registre y dashboards y demas secciones acorde a la base de datos que me diste.
```

### Resultado esperado

- Landing page institucional.
- Secciones de bienvenida.
- Logo visual.
- Accesos a login, registro y dashboard.
- Relacion visual con la base de datos del proyecto.

## Prompt 5. Prioridades de desarrollo siguientes

```text
Implementar autenticacion y roles.
Crear DTOs de respuesta para no exponer entidades JPA.
Agregar CRUD completo con update/delete y consultas por id.
Escribir pruebas unitarias de PrestamoService e IncidenciaService.
Separar perfiles dev con H2 y prod con MySQL.
Convertir la landing en pantallas reales de login, registro, dashboard y modulos.
Seguir esto paso a paso y documentar prompts en Promts/Documentation.md.
```

### Resultado esperado

- Seguridad con roles.
- DTOs de respuesta.
- CRUD mas completo.
- Pruebas unitarias.
- Configuracion por perfiles.
- Pantallas reales del sistema.
- Evidencia documental de prompts.

## Prompt 6. Aceleracion del cierre de entrega

```text
Requiero que sea mas rapido el desarrollo siguiendo las instrucciones. 
```

### Resultado esperado

- Priorizacion de tareas criticas.
- Correccion rapida de compilacion.
- Cierre funcional del sistema.
- Ajustes finales para entrega.

## Prompts tecnicos derivados usados durante la construccion

### Seguridad y usuarios

```text
Agrega autenticacion con Spring Security, roles ADMIN, COORDINACION y SOPORTE, login personalizado, registro de usuarios y usuarios semilla para el sistema.
```

### DTOs y API

```text
Reemplaza las respuestas directas de entidades JPA por DTOs de salida y actualiza los controladores para entregar respuestas mas seguras y mantenibles.
```

### CRUD y reglas de negocio

```text
Completa el CRUD con consultas por id, actualizacion y eliminacion controlada para campers, computadores, prestamos e incidencias, respetando reglas del dominio.
```

### Perfiles de entorno

```text
Separa la configuracion del proyecto en perfil dev con H2 y perfil prod con MySQL.
```

### Pruebas unitarias

```text
Escribe pruebas unitarias con Mockito para PrestamoService e IncidenciaService validando casos exitosos y reglas de negocio.
```

### Pantallas reales

```text
Convierte la landing en una interfaz real con vistas para login, registro, dashboard y modulos del sistema.
```

## Evidencia de resultados obtenidos con IA

- Creacion del modulo de autenticacion con usuarios y roles.
- Generacion de DTOs de respuesta para API.
- Ampliacion de controladores con operaciones CRUD.
- Creacion de perfiles `dev` y `prod`.
- Diseno de pantallas `index`, `login`, `register`, `dashboard` y modulos.
- Generacion de pruebas unitarias para servicios criticos.
- Ajuste de script SQL para incluir usuarios del sistema.

## Observacion final

Los prompts fueron refinados durante el desarrollo segun el avance del proyecto. Este documento resume los prompts principales y tecnicos que guiaron la construccion del sistema con apoyo de IA.
