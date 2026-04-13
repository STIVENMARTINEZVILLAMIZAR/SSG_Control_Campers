package com.campus.campus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.ComputadorRequest;
import com.campus.campus.dto.ComputadorUpdateRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.exception.ResourceNotFoundException;
import com.campus.campus.model.Computador;
import com.campus.campus.repository.IncidenciaRepository;
import com.campus.campus.repository.ComputadorRepository;
import com.campus.campus.repository.PrestamoComputadorRepository;

@Service
public class ComputadorService {

	private final ComputadorRepository computadorRepository;
	private final PrestamoComputadorRepository prestamoRepository;
	private final IncidenciaRepository incidenciaRepository;

	public ComputadorService(
			ComputadorRepository computadorRepository,
			PrestamoComputadorRepository prestamoRepository,
			IncidenciaRepository incidenciaRepository) {
		this.computadorRepository = computadorRepository;
		this.prestamoRepository = prestamoRepository;
		this.incidenciaRepository = incidenciaRepository;
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
		validarDuplicados(null, request.serial(), request.placaInventario());

		Computador computador = new Computador();
		computador.setSerial(request.serial());
		computador.setPlacaInventario(request.placaInventario());
		computador.setMarca(request.marca());
		computador.setModelo(request.modelo());
		computador.setUbicacion(request.ubicacion());
		return computadorRepository.save(computador);
	}

	@Transactional
	public Computador actualizar(Long id, ComputadorUpdateRequest request) {
		Computador computador = obtenerPorId(id);
		validarDuplicados(id, request.serial(), request.placaInventario());

		computador.setSerial(request.serial());
		computador.setPlacaInventario(request.placaInventario());
		computador.setMarca(request.marca());
		computador.setModelo(request.modelo());
		computador.setUbicacion(request.ubicacion());
		computador.setEstado(request.estado());
		return computador;
	}

	@Transactional
	public void eliminar(Long id) {
		Computador computador = obtenerPorId(id);
		if (prestamoRepository.existsByComputadorId(computador.getId()) || incidenciaRepository.existsByComputadorId(computador.getId())) {
			throw new BusinessException("No se puede eliminar el computador porque tiene historial operativo asociado.");
		}
		computadorRepository.delete(computador);
	}

	private void validarDuplicados(Long computadorId, String serial, String placaInventario) {
		computadorRepository.findAll().stream()
				.filter(computador -> computador.getSerial().equalsIgnoreCase(serial))
				.filter(computador -> !computador.getId().equals(computadorId))
				.findFirst()
				.ifPresent(computador -> {
					throw new BusinessException("Ya existe un computador con ese serial.");
				});

		computadorRepository.findAll().stream()
				.filter(computador -> computador.getPlacaInventario().equalsIgnoreCase(placaInventario))
				.filter(computador -> !computador.getId().equals(computadorId))
				.findFirst()
				.ifPresent(computador -> {
					throw new BusinessException("Ya existe un computador con esa placa de inventario.");
				});
	}
}
