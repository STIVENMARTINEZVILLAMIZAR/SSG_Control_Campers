package com.campus.campus.dto;

import com.campus.campus.model.RolUsuario;

public record UsuarioResponse(
		Long id,
		String nombreCompleto,
		String correo,
		RolUsuario rol,
		boolean activo) {
}
