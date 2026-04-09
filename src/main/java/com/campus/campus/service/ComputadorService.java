package com.campus.campus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.ComputadorRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.exception.ResourceNotFoundException;
import com.campus.campus.model.Computador;
import com.campus.campus.repository.ComputadorRepository;

@Service
public class ComputadorService {

	private final ComputadorRepository computadorRepository;

	public ComputadorService(ComputadorRepository computadorRepository) {
		this.computadorRepository = computadorRepository;
	}

	@Transactional(readOnly = true)
	public List<Computador> listar() {
		return computadorRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Computador obtenerPorId(Long id) {
		return computadorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el computador con id " + id));
	}

	@Transactional
	public Computador crear(ComputadorRequest request) {
		if (computadorRepository.existsBySerial(request.serial())) {
			throw new BusinessException("Ya existe un computador con ese serial.");
		}
		if (computadorRepository.existsByPlacaInventario(request.placaInventario())) {
			throw new BusinessException("Ya existe un computador con esa placa de inventario.");
		}

		Computador computador = new Computador();
		computador.setSerial(request.serial());
		computador.setPlacaInventario(request.placaInventario());
		computador.setMarca(request.marca());
		computador.setModelo(request.modelo());
		computador.setUbicacion(request.ubicacion());
		return computadorRepository.save(computador);
	}
}
