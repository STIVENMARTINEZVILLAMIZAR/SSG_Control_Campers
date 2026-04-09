package com.campus.campus.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DevolucionRequest(
		@NotNull LocalDate fechaEntregaReal,
		@Size(max = 250) String observaciones) {
}
