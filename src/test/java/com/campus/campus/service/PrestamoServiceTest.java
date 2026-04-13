package com.campus.campus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.campus.campus.dto.DevolucionRequest;
import com.campus.campus.dto.PrestamoRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.model.Camper;
import com.campus.campus.model.Computador;
import com.campus.campus.model.EstadoComputador;
import com.campus.campus.model.EstadoPrestamo;
import com.campus.campus.model.PrestamoComputador;
import com.campus.campus.repository.IncidenciaRepository;
import com.campus.campus.repository.PrestamoComputadorRepository;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

	@Mock
	private PrestamoComputadorRepository prestamoRepository;

	@Mock
	private CamperService camperService;

	@Mock
	private ComputadorService computadorService;

	@Mock
	private IncidenciaRepository incidenciaRepository;

	@InjectMocks
	private PrestamoService prestamoService;

	@Test
	void debeCrearPrestamoYAsignarComputador() {
		PrestamoRequest request = new PrestamoRequest(
				1L,
				2L,
				LocalDate.now(),
				LocalDate.now().plusDays(5),
				"Prestamo de prueba");

		Camper camper = new Camper();
		camper.setId(1L);
		camper.setActivo(true);

		Computador computador = new Computador();
		computador.setId(2L);
		computador.setEstado(EstadoComputador.DISPONIBLE);

		when(prestamoRepository.existsByCamperIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(false);
		when(prestamoRepository.existsByComputadorIdAndEstado(2L, EstadoPrestamo.ACTIVO)).thenReturn(false);
		when(camperService.obtenerPorId(1L)).thenReturn(camper);
		when(computadorService.obtenerPorId(2L)).thenReturn(computador);
		when(prestamoRepository.save(any(PrestamoComputador.class))).thenAnswer(invocation -> invocation.getArgument(0));

		PrestamoComputador prestamo = prestamoService.crear(request);

		assertEquals(EstadoPrestamo.ACTIVO, prestamo.getEstado());
		assertEquals(EstadoComputador.ASIGNADO, computador.getEstado());
		assertEquals(LocalDate.now().plusDays(5), prestamo.getFechaDevolucionProgramada());
	}

	@Test
	void noDebeCrearPrestamoSiComputadorNoEstaDisponible() {
		PrestamoRequest request = new PrestamoRequest(
				1L,
				2L,
				LocalDate.now(),
				LocalDate.now().plusDays(5),
				"Prestamo de prueba");

		Camper camper = new Camper();
		camper.setId(1L);
		camper.setActivo(true);

		Computador computador = new Computador();
		computador.setId(2L);
		computador.setEstado(EstadoComputador.MANTENIMIENTO);

		when(prestamoRepository.existsByCamperIdAndEstado(1L, EstadoPrestamo.ACTIVO)).thenReturn(false);
		when(prestamoRepository.existsByComputadorIdAndEstado(2L, EstadoPrestamo.ACTIVO)).thenReturn(false);
		when(camperService.obtenerPorId(1L)).thenReturn(camper);
		when(computadorService.obtenerPorId(2L)).thenReturn(computador);

		assertThrows(BusinessException.class, () -> prestamoService.crear(request));
	}

	@Test
	void debeDevolverPrestamoFinalizadoYLiberarComputador() {
		Computador computador = new Computador();
		computador.setEstado(EstadoComputador.ASIGNADO);

		PrestamoComputador prestamo = new PrestamoComputador();
		prestamo.setId(10L);
		prestamo.setEstado(EstadoPrestamo.ACTIVO);
		prestamo.setFechaAsignacion(LocalDate.now());
		prestamo.setFechaDevolucionProgramada(LocalDate.now().plusDays(2));
		prestamo.setComputador(computador);

		when(prestamoRepository.findById(10L)).thenReturn(Optional.of(prestamo));
		when(incidenciaRepository.existsByPrestamoId(10L)).thenReturn(false);

		PrestamoComputador resultado = prestamoService.devolver(
				10L,
				new DevolucionRequest(LocalDate.now().plusDays(1), "Entrega normal"));

		assertEquals(EstadoPrestamo.FINALIZADO, resultado.getEstado());
		assertEquals(EstadoComputador.DISPONIBLE, computador.getEstado());
	}

	@Test
	void debeCancelarPrestamoActivo() {
		Computador computador = new Computador();
		computador.setEstado(EstadoComputador.ASIGNADO);

		PrestamoComputador prestamo = new PrestamoComputador();
		prestamo.setId(20L);
		prestamo.setEstado(EstadoPrestamo.ACTIVO);
		prestamo.setComputador(computador);

		when(prestamoRepository.findById(20L)).thenReturn(Optional.of(prestamo));

		PrestamoComputador resultado = prestamoService.cancelar(20L);

		assertEquals(EstadoPrestamo.CANCELADO, resultado.getEstado());
		assertEquals(EstadoComputador.DISPONIBLE, computador.getEstado());
	}
}
