package com.campus.campus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.CamperRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.exception.ResourceNotFoundException;
import com.campus.campus.model.Camper;
import com.campus.campus.repository.CamperRepository;

@Service
public class CamperService {

	private final CamperRepository camperRepository;

	public CamperService(CamperRepository camperRepository) {
		this.camperRepository = camperRepository;
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
		if (camperRepository.existsByDocumento(request.documento())) {
			throw new BusinessException("Ya existe un camper registrado con ese documento.");
		}
		if (camperRepository.existsByCorreo(request.correo())) {
			throw new BusinessException("Ya existe un camper registrado con ese correo.");
		}

		Camper camper = new Camper();
		camper.setDocumento(request.documento());
		camper.setNombreCompleto(request.nombreCompleto());
		camper.setCorreo(request.correo());
		camper.setClan(request.clan());
		camper.setActivo(request.activo());
		return camperRepository.save(camper);
	}
}
