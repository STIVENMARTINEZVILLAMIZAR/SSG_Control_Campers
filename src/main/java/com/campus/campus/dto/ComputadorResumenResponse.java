package com.campus.campus.dto;

import com.campus.campus.model.EstadoComputador;

public record ComputadorResumenResponse(
		Long id,
		String serial,
		String placaInventario,
		EstadoComputador estado) {
}
