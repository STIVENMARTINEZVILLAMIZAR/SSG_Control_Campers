package com.campus.campus.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.IncidenciaRequest;
import com.campus.campus.exception.ResourceNotFoundException;
import com.campus.campus.model.Computador;
import com.campus.campus.model.EstadoComputador;
import com.campus.campus.model.Incidencia;
import com.campus.campus.model.PrestamoComputador;
import com.campus.campus.repository.IncidenciaRepository;
import com.campus.campus.repository.PrestamoComputadorRepository;

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

	@Transactional
	public Incidencia crear(IncidenciaRequest request) {
		Computador computador = computadorService.obtenerPorId(request.computadorId());

		PrestamoComputador prestamo = null;
		if (request.prestamoId() != null) {
			prestamo = prestamoRepository.findById(request.prestamoId())
					.orElseThrow(() -> new ResourceNotFoundException("No existe el prestamo con id " + request.prestamoId()));
		}

		Incidencia incidencia = new Incidencia();
		incidencia.setComputador(computador);
		incidencia.setPrestamo(prestamo);
		incidencia.setFechaReporte(LocalDate.now());
		incidencia.setTipo(request.tipo());
		incidencia.setSeveridad(request.severidad());
		incidencia.setDescripcion(request.descripcion());
		incidencia.setReportadoPor(request.reportadoPor());

		if (request.severidad().ordinal() >= 2) {
			computador.setEstado(EstadoComputador.MANTENIMIENTO);
		}

		return incidenciaRepository.save(incidencia);
	}
}
