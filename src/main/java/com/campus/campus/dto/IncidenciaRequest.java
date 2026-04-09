package com.campus.campus.dto;

import com.campus.campus.model.SeveridadIncidencia;
import com.campus.campus.model.TipoIncidencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidenciaRequest(
		@NotNull Long computadorId,
		Long prestamoId,
		@NotNull TipoIncidencia tipo,
		@NotNull SeveridadIncidencia severidad,
		@NotBlank @Size(max = 500) String descripcion,
		@NotBlank @Size(max = 100) String reportadoPor) {
}
