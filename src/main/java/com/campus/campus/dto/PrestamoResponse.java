package com.campus.campus.dto;

import java.time.LocalDate;

import com.campus.campus.model.EstadoPrestamo;

public record PrestamoResponse(
		Long id,
		CamperResumenResponse camper,
		ComputadorResumenResponse computador,
		LocalDate fechaAsignacion,
		LocalDate fechaDevolucionProgramada,
		LocalDate fechaEntregaReal,
		EstadoPrestamo estado,
		String observaciones) {
}
