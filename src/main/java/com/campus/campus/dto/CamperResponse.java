package com.campus.campus.dto;

public record CamperResponse(
		Long id,
		String documento,
		String nombreCompleto,
		String correo,
		String clan,
		boolean activo) {
}
