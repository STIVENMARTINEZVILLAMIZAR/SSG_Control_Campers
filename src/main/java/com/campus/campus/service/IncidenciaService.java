package com.campus.campus.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.IncidenciaRequest;
import com.campus.campus.dto.IncidenciaUpdateRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.exception.ResourceNotFoundException;
import com.campus.campus.model.Computador;
import com.campus.campus.model.EstadoComputador;
import com.campus.campus.model.EstadoIncidencia;
import com.campus.campus.model.EstadoPrestamo;
import com.campus.campus.model.Incidencia;
import com.campus.campus.model.PrestamoComputador;
import com.campus.campus.model.SeveridadIncidencia;
import com.campus.campus.repository.IncidenciaRepository;
import com.campus.campus.repository.PrestamoComputadorRepository;

import java.time.LocalDate;

@Service
public class IncidenciaService {

	private final IncidenciaRepository incidenciaRepository;
	private final ComputadorService computadorService;
	private final PrestamoComputadorRepository prestamoRepository;

	public IncidenciaService(
			IncidenciaRepository incidenciaRepository,
			ComputadorService computadorService,
			PrestamoComputadorRepository prestamoRepository) {
		this.incidenciaRepository = incidenciaRepository;
		this.computadorService = computadorService;
		this.prestamoRepository = prestamoRepository;
	}

	@Transactional(readOnly = true)
	public List<Incidencia> listar() {
		return incidenciaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Incidencia obtenerPorId(Long id) {
		return incidenciaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe la incidencia con id " + id));
	}

	@Transactional
	public Incidencia crear(IncidenciaRequest request) {
		Computador computador = computadorService.obtenerPorId(request.computadorId());
		PrestamoComputador prestamo = validarPrestamoRelacionado(request.prestamoId(), computador.getId());

		Incidencia incidencia = new Incidencia();
		incidencia.setComputador(computador);
		incidencia.setPrestamo(prestamo);
		incidencia.setFechaReporte(LocalDate.now());
		incidencia.setTipo(request.tipo());
		incidencia.setSeveridad(request.severidad());
		incidencia.setDescripcion(request.descripcion());
		incidencia.setReportadoPor(request.reportadoPor());
		ajustarEstadoComputador(computador, request.severidad(), EstadoIncidencia.ABIERTA);

		return incidenciaRepository.save(incidencia);
	}

	@Transactional
	public Incidencia actualizar(Long incidenciaId, IncidenciaUpdateRequest request) {
		Incidencia incidencia = obtenerPorId(incidenciaId);
		PrestamoComputador prestamo = validarPrestamoRelacionado(request.prestamoId(), incidencia.getComputador().getId());

		incidencia.setPrestamo(prestamo);
		incidencia.setTipo(request.tipo());
		incidencia.setSeveridad(request.severidad());
		incidencia.setEstado(request.estado());
		incidencia.setDescripcion(request.descripcion());
		incidencia.setReportadoPor(request.reportadoPor());

		recalcularEstadoComputador(incidencia.getComputador());
		return incidencia;
	}

	@Transactional
	public void eliminar(Long incidenciaId) {
		Incidencia incidencia = obtenerPorId(incidenciaId);
		Computador computador = incidencia.getComputador();
		incidenciaRepository.delete(incidencia);
		incidenciaRepository.flush();
		recalcularEstadoComputador(computador);
	}

	private PrestamoComputador validarPrestamoRelacionado(Long prestamoId, Long computadorId) {
		if (prestamoId == null) {
			return null;
		}

		PrestamoComputador prestamo = prestamoRepository.findById(prestamoId)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el prestamo con id " + prestamoId));

		if (!prestamo.getComputador().getId().equals(computadorId)) {
			throw new BusinessException("El prestamo no corresponde al computador reportado.");
		}

		return prestamo;
	}

	private void ajustarEstadoComputador(Computador computador, SeveridadIncidencia severidad, EstadoIncidencia estado) {
		if (estado != EstadoIncidencia.CERRADA && estado != EstadoIncidencia.RESUELTA && severidad.ordinal() >= 2) {
			computador.setEstado(EstadoComputador.MANTENIMIENTO);
		}
	}

	private void recalcularEstadoComputador(Computador computador) {
		long incidenciasActivasGraves = incidenciaRepository.countByComputadorIdAndEstadoInAndSeveridadIn(
				computador.getId(),
				List.of(EstadoIncidencia.ABIERTA, EstadoIncidencia.EN_ANALISIS),
				List.of(SeveridadIncidencia.ALTA, SeveridadIncidencia.CRITICA));

		if (incidenciasActivasGraves > 0) {
			computador.setEstado(EstadoComputador.MANTENIMIENTO);
			return;
		}

		boolean tienePrestamoActivo = prestamoRepository.findByComputadorIdAndEstado(computador.getId(), EstadoPrestamo.ACTIVO).isPresent();
		computador.setEstado(tienePrestamoActivo ? EstadoComputador.ASIGNADO : EstadoComputador.DISPONIBLE);
	}
}
