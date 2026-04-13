package com.campus.campus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CamperUpdateRequest(
		@NotBlank @Size(max = 20) String documento,
		@NotBlank @Size(max = 120) String nombreCompleto,
		@NotBlank @Email @Size(max = 100) String correo,
		@NotBlank @Size(max = 50) String clan,
		boolean activo) {
}
