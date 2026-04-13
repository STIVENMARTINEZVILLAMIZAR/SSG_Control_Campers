package com.campus.campus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.campus.campus.dto.IncidenciaRequest;
import com.campus.campus.dto.IncidenciaUpdateRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.model.Computador;
import com.campus.campus.model.EstadoComputador;
import com.campus.campus.model.EstadoIncidencia;
import com.campus.campus.model.EstadoPrestamo;
import com.campus.campus.model.Incidencia;
import com.campus.campus.model.PrestamoComputador;
import com.campus.campus.model.SeveridadIncidencia;
import com.campus.campus.model.TipoIncidencia;
import com.campus.campus.repository.IncidenciaRepository;
import com.campus.campus.repository.PrestamoComputadorRepository;

@ExtendWith(MockitoExtension.class)
class IncidenciaServiceTest {

	@Mock
	private IncidenciaRepository incidenciaRepository;

	@Mock
	private ComputadorService computadorService;

	@Mock
	private PrestamoComputadorRepository prestamoRepository;

	@InjectMocks
	private IncidenciaService incidenciaService;

	@Test
	void debeCrearIncidenciaCriticaYEnviarEquipoAMantenimiento() {
		Computador computador = new Computador();
		computador.setId(1L);
		computador.setEstado(EstadoComputador.DISPONIBLE);

		when(computadorService.obtenerPorId(1L)).thenReturn(computador);
		when(incidenciaRepository.save(any(Incidencia.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Incidencia incidencia = incidenciaService.crear(new IncidenciaRequest(
				1L,
				null,
				TipoIncidencia.HARDWARE,
				SeveridadIncidencia.CRITICA,
				"Pantalla averiada",
				"Soporte"));

		assertEquals(EstadoComputador.MANTENIMIENTO, computador.getEstado());
		assertEquals(EstadoIncidencia.ABIERTA, incidencia.getEstado());
	}

	@Test
	void noDebeAsociarPrestamoDeOtroComputador() {
		Computador computador = new Computador();
		computador.setId(1L);

		Computador otroComputador = new Computador();
		otroComputador.setId(9L);

		PrestamoComputador prestamo = new PrestamoComputador();
		prestamo.setId(7L);
		prestamo.setComputador(otroComputador);

		when(computadorService.obtenerPorId(1L)).thenReturn(computador);
		when(prestamoRepository.findById(7L)).thenReturn(Optional.of(prestamo));

		assertThrows(BusinessException.class, () -> incidenciaService.crear(new IncidenciaRequest(
				1L,
				7L,
				TipoIncidencia.SOFTWARE,
				SeveridadIncidencia.ALTA,
				"Falla de software",
				"Soporte")));
	}

	@Test
	void debeRecalcularEstadoComputadorAlActualizarIncidenciaResuelta() {
		Computador computador = new Computador();
		computador.setId(5L);
		computador.setEstado(EstadoComputador.MANTENIMIENTO);

		PrestamoComputador prestamo = new PrestamoComputador();
		prestamo.setId(3L);
		prestamo.setComputador(computador);
		prestamo.setEstado(EstadoPrestamo.ACTIVO);

		Incidencia incidencia = new Incidencia();
		incidencia.setId(15L);
		incidencia.setComputador(computador);

		when(incidenciaRepository.findById(15L)).thenReturn(Optional.of(incidencia));
		when(prestamoRepository.findById(3L)).thenReturn(Optional.of(prestamo));
		when(incidenciaRepository.countByComputadorIdAndEstadoInAndSeveridadIn(
				org.mockito.ArgumentMatchers.eq(5L),
				any(List.class),
				any(List.class))).thenReturn(0L);
		when(prestamoRepository.findByComputadorIdAndEstado(5L, EstadoPrestamo.ACTIVO)).thenReturn(Optional.of(prestamo));

		Incidencia actualizada = incidenciaService.actualizar(15L, new IncidenciaUpdateRequest(
				3L,
				TipoIncidencia.SOFTWARE,
				SeveridadIncidencia.MEDIA,
				EstadoIncidencia.RESUELTA,
				"Incidencia atendida",
				"Soporte"));

		assertEquals(EstadoIncidencia.RESUELTA, actualizada.getEstado());
		assertEquals(EstadoComputador.ASIGNADO, computador.getEstado());
	}
}
