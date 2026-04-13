package com.campus.campus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.DashboardResumenResponse;
import com.campus.campus.model.EstadoComputador;
import com.campus.campus.model.EstadoIncidencia;
import com.campus.campus.model.EstadoPrestamo;
import com.campus.campus.model.SeveridadIncidencia;
import com.campus.campus.repository.CamperRepository;
import com.campus.campus.repository.ComputadorRepository;
import com.campus.campus.repository.IncidenciaRepository;
import com.campus.campus.repository.PrestamoComputadorRepository;

@Service
public class DashboardService {

	private final CamperRepository camperRepository;
	private final ComputadorRepository computadorRepository;
	private final PrestamoComputadorRepository prestamoRepository;
	private final IncidenciaRepository incidenciaRepository;

	public DashboardService(
			CamperRepository camperRepository,
			ComputadorRepository computadorRepository,
			PrestamoComputadorRepository prestamoRepository,
			IncidenciaRepository incidenciaRepository) {
		this.camperRepository = camperRepository;
		this.computadorRepository = computadorRepository;
		this.prestamoRepository = prestamoRepository;
		this.incidenciaRepository = incidenciaRepository;
	}

	@Transactional(readOnly = true)
	public DashboardResumenResponse resumen() {
		return new DashboardResumenResponse(
				camperRepository.countByActivoTrue(),
				computadorRepository.count(),
				computadorRepository.countByEstado(EstadoComputador.DISPONIBLE),
				computadorRepository.countByEstado(EstadoComputador.ASIGNADO),
				computadorRepository.countByEstado(EstadoComputador.MANTENIMIENTO),
				prestamoRepository.countByEstado(EstadoPrestamo.ACTIVO),
				incidenciaRepository.countByEstado(EstadoIncidencia.ABIERTA),
				incidenciaRepository.countBySeveridad(SeveridadIncidencia.CRITICA));
	}
}
