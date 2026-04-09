package com.campus.campus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.DevolucionRequest;
import com.campus.campus.dto.PrestamoRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.exception.ResourceNotFoundException;
import com.campus.campus.model.Camper;
import com.campus.campus.model.Computador;
import com.campus.campus.model.EstadoComputador;
import com.campus.campus.model.EstadoPrestamo;
import com.campus.campus.model.PrestamoComputador;
import com.campus.campus.repository.PrestamoComputadorRepository;

@Service
public class PrestamoService {

	private final PrestamoComputadorRepository prestamoRepository;
	private final CamperService camperService;
	private final ComputadorService computadorService;

	public PrestamoService(
			PrestamoComputadorRepository prestamoRepository,
			CamperService camperService,
			ComputadorService computadorService) {
		this.prestamoRepository = prestamoRepository;
		this.camperService = camperService;
		this.computadorService = computadorService;
	}

	@Transactional(readOnly = true)
	public List<PrestamoComputador> listar() {
		return prestamoRepository.findAll();
	}

	@Transactional
	public PrestamoComputador crear(PrestamoRequest request) {
		if (request.fechaDevolucionProgramada().isBefore(request.fechaAsignacion())) {
			throw new BusinessException("La fecha de devolucion no puede ser anterior a la fecha de asignacion.");
		}
		if (prestamoRepository.existsByCamperIdAndEstado(request.camperId(), EstadoPrestamo.ACTIVO)) {
			throw new BusinessException("El camper ya tiene un prestamo activo.");
		}
		if (prestamoRepository.existsByComputadorIdAndEstado(request.computadorId(), EstadoPrestamo.ACTIVO)) {
			throw new BusinessException("El computador ya se encuentra asignado.");
		}

		Camper camper = camperService.obtenerPorId(request.camperId());
		if (!camper.isActivo()) {
			throw new BusinessException("No se puede asignar un computador a un camper inactivo.");
		}

		Computador computador = computadorService.obtenerPorId(request.computadorId());
		if (computador.getEstado() != EstadoComputador.DISPONIBLE) {
			throw new BusinessException("El computador no se encuentra disponible para prestamo.");
		}

		PrestamoComputador prestamo = new PrestamoComputador();
		prestamo.setCamper(camper);
		prestamo.setComputador(computador);
		prestamo.setFechaAsignacion(request.fechaAsignacion());
		prestamo.setFechaDevolucionProgramada(request.fechaDevolucionProgramada());
		prestamo.setObservaciones(request.observaciones());
		prestamo.setEstado(EstadoPrestamo.ACTIVO);

		computador.setEstado(EstadoComputador.ASIGNADO);
		return prestamoRepository.save(prestamo);
	}

	@Transactional
	public PrestamoComputador devolver(Long prestamoId, DevolucionRequest request) {
		PrestamoComputador prestamo = prestamoRepository.findById(prestamoId)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el prestamo con id " + prestamoId));

		if (prestamo.getEstado() != EstadoPrestamo.ACTIVO) {
			throw new BusinessException("Solo se pueden finalizar prestamos en estado ACTIVO.");
		}
		if (request.fechaEntregaReal().isBefore(prestamo.getFechaAsignacion())) {
			throw new BusinessException("La fecha de entrega real no puede ser anterior a la fecha de asignacion.");
		}

		prestamo.setFechaEntregaReal(request.fechaEntregaReal());
		prestamo.setObservaciones(request.observaciones());
		prestamo.setEstado(
				request.fechaEntregaReal().isAfter(prestamo.getFechaDevolucionProgramada())
						? EstadoPrestamo.ATRASADO
						: EstadoPrestamo.FINALIZADO);
		prestamo.getComputador().setEstado(EstadoComputador.DISPONIBLE);
		return prestamo;
	}
}
