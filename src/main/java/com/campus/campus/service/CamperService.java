package com.campus.campus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.CamperRequest;
import com.campus.campus.dto.CamperUpdateRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.exception.ResourceNotFoundException;
import com.campus.campus.model.Camper;
import com.campus.campus.repository.PrestamoComputadorRepository;
import com.campus.campus.repository.CamperRepository;

@Service
public class CamperService {

	private final CamperRepository camperRepository;
	private final PrestamoComputadorRepository prestamoRepository;

	public CamperService(CamperRepository camperRepository, PrestamoComputadorRepository prestamoRepository) {
		this.camperRepository = camperRepository;
		this.prestamoRepository = prestamoRepository;
	}

	@Transactional(readOnly = true)
	public List<Camper> listar() {
		return camperRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Camper obtenerPorId(Long id) {
		return camperRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el camper con id " + id));
	}

	@Transactional
	public Camper crear(CamperRequest request) {
		validarDuplicados(null, request.documento(), request.correo());

		Camper camper = new Camper();
		camper.setDocumento(request.documento());
		camper.setNombreCompleto(request.nombreCompleto());
		camper.setCorreo(request.correo());
		camper.setClan(request.clan());
		camper.setActivo(request.activo());
		return camperRepository.save(camper);
	}

	@Transactional
	public Camper actualizar(Long id, CamperUpdateRequest request) {
		Camper camper = obtenerPorId(id);
		validarDuplicados(id, request.documento(), request.correo());

		camper.setDocumento(request.documento());
		camper.setNombreCompleto(request.nombreCompleto());
		camper.setCorreo(request.correo());
		camper.setClan(request.clan());
		camper.setActivo(request.activo());
		return camper;
	}

	@Transactional
	public void eliminar(Long id) {
		Camper camper = obtenerPorId(id);
		if (prestamoRepository.existsByCamperId(camper.getId())) {
			throw new BusinessException("No se puede eliminar el camper porque tiene historial de prestamos.");
		}
		camperRepository.delete(camper);
	}

	private void validarDuplicados(Long camperId, String documento, String correo) {
		camperRepository.findByDocumento(documento)
				.filter(camper -> !camper.getId().equals(camperId))
				.ifPresent(camper -> {
					throw new BusinessException("Ya existe un camper registrado con ese documento.");
				});

		camperRepository.findAll().stream()
				.filter(camper -> camper.getCorreo().equalsIgnoreCase(correo))
				.filter(camper -> !camper.getId().equals(camperId))
				.findFirst()
				.ifPresent(camper -> {
					throw new BusinessException("Ya existe un camper registrado con ese correo.");
				});
	}
}
