package com.campus.campus.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PrestamoUpdateRequest(
		@NotNull @FutureOrPresent LocalDate fechaDevolucionProgramada,
		@Size(max = 250) String observaciones) {
}
