package com.campus.campus.dto;

import com.campus.campus.model.EstadoIncidencia;
import com.campus.campus.model.SeveridadIncidencia;
import com.campus.campus.model.TipoIncidencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidenciaUpdateRequest(
		Long prestamoId,
		@NotNull TipoIncidencia tipo,
		@NotNull SeveridadIncidencia severidad,
		@NotNull EstadoIncidencia estado,
		@NotBlank @Size(max = 500) String descripcion,
		@NotBlank @Size(max = 100) String reportadoPor) {
}
