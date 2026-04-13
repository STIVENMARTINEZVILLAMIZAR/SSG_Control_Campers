package com.campus.campus.dto;

import com.campus.campus.model.EstadoComputador;

public record ComputadorResponse(
		Long id,
		String serial,
		String placaInventario,
		String marca,
		String modelo,
		String ubicacion,
		EstadoComputador estado) {
}
