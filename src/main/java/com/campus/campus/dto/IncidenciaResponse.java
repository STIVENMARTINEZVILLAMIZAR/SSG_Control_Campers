package com.campus.campus.dto;

import java.time.LocalDate;

import com.campus.campus.model.EstadoIncidencia;
import com.campus.campus.model.SeveridadIncidencia;
import com.campus.campus.model.TipoIncidencia;

public record IncidenciaResponse(
		Long id,
		ComputadorResumenResponse computador,
		Long prestamoId,
		LocalDate fechaReporte,
		TipoIncidencia tipo,
		SeveridadIncidencia severidad,
		EstadoIncidencia estado,
		String descripcion,
		String reportadoPor) {
}
