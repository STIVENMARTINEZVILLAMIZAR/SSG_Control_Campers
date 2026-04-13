package com.campus.campus.dto;

import com.campus.campus.model.EstadoComputador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComputadorUpdateRequest(
		@NotBlank @Size(max = 50) String serial,
		@NotBlank @Size(max = 30) String placaInventario,
		@NotBlank @Size(max = 60) String marca,
		@NotBlank @Size(max = 60) String modelo,
		@NotBlank @Size(max = 80) String ubicacion,
		@NotNull EstadoComputador estado) {
}
