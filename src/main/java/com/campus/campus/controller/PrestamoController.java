package com.campus.campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campus.campus.dto.DevolucionRequest;
import com.campus.campus.dto.PrestamoRequest;
import com.campus.campus.dto.PrestamoResponse;
import com.campus.campus.dto.PrestamoUpdateRequest;
import com.campus.campus.mapper.ApiMapper;
import com.campus.campus.service.PrestamoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

	private final PrestamoService prestamoService;
	private final ApiMapper apiMapper;

	public PrestamoController(PrestamoService prestamoService, ApiMapper apiMapper) {
		this.prestamoService = prestamoService;
		this.apiMapper = apiMapper;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION','SOPORTE')")
	public List<PrestamoResponse> listar() {
		return prestamoService.listar().stream().map(apiMapper::toResponse).toList();
	}

	@GetMapping("/{prestamoId}")
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION','SOPORTE')")
	public PrestamoResponse obtenerPorId(@PathVariable Long prestamoId) {
		return apiMapper.toResponse(prestamoService.obtenerPorId(prestamoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION')")
	public PrestamoResponse crear(@Valid @RequestBody PrestamoRequest request) {
		return apiMapper.toResponse(prestamoService.crear(request));
	}

	@PutMapping("/{prestamoId}")
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION')")
	public PrestamoResponse actualizar(
			@PathVariable Long prestamoId,
			@Valid @RequestBody PrestamoUpdateRequest request) {
		return apiMapper.toResponse(prestamoService.actualizar(prestamoId, request));
	}

	@PatchMapping("/{prestamoId}/devolucion")
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION')")
	public PrestamoResponse devolver(
			@PathVariable Long prestamoId,
			@Valid @RequestBody DevolucionRequest request) {
		return apiMapper.toResponse(prestamoService.devolver(prestamoId, request));
	}

	@PatchMapping("/{prestamoId}/cancelacion")
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION')")
	public PrestamoResponse cancelar(@PathVariable Long prestamoId) {
		return apiMapper.toResponse(prestamoService.cancelar(prestamoId));
	}

	@DeleteMapping("/{prestamoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public void eliminar(@PathVariable Long prestamoId) {
		prestamoService.eliminar(prestamoId);
	}
}
