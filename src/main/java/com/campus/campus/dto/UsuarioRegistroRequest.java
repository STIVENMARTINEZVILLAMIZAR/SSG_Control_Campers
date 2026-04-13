package com.campus.campus.dto;

import com.campus.campus.model.RolUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRegistroRequest(
		@NotBlank @Size(max = 120) String nombreCompleto,
		@NotBlank @Email @Size(max = 120) String correo,
		@NotBlank @Size(min = 8, max = 60) String password,
		@NotNull RolUsuario rol) {
}
