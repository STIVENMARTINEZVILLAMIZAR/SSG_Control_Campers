package com.campus.campus.dto;

public record DashboardResumenResponse(
		long totalCampersActivos,
		long totalComputadores,
		long computadoresDisponibles,
		long computadoresAsignados,
		long computadoresEnMantenimiento,
		long prestamosActivos,
		long incidenciasAbiertas,
		long incidenciasCriticas) {
}
