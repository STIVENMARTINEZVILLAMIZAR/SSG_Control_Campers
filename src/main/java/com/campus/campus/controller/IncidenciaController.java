package com.campus.campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campus.campus.dto.IncidenciaRequest;
import com.campus.campus.dto.IncidenciaResponse;
import com.campus.campus.dto.IncidenciaUpdateRequest;
import com.campus.campus.mapper.ApiMapper;
import com.campus.campus.service.IncidenciaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

	private final IncidenciaService incidenciaService;
	private final ApiMapper apiMapper;

	public IncidenciaController(IncidenciaService incidenciaService, ApiMapper apiMapper) {
		this.incidenciaService = incidenciaService;
		this.apiMapper = apiMapper;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION','SOPORTE')")
	public List<IncidenciaResponse> listar() {
		return incidenciaService.listar().stream().map(apiMapper::toResponse).toList();
	}

	@GetMapping("/{incidenciaId}")
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION','SOPORTE')")
	public IncidenciaResponse obtenerPorId(@PathVariable Long incidenciaId) {
		return apiMapper.toResponse(incidenciaService.obtenerPorId(incidenciaId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole('ADMIN','SOPORTE')")
	public IncidenciaResponse crear(@Valid @RequestBody IncidenciaRequest request) {
		return apiMapper.toResponse(incidenciaService.crear(request));
	}

	@PutMapping("/{incidenciaId}")
	@PreAuthorize("hasAnyRole('ADMIN','SOPORTE')")
	public IncidenciaResponse actualizar(
			@PathVariable Long incidenciaId,
			@Valid @RequestBody IncidenciaUpdateRequest request) {
		return apiMapper.toResponse(incidenciaService.actualizar(incidenciaId, request));
	}

	@DeleteMapping("/{incidenciaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public void eliminar(@PathVariable Long incidenciaId) {
		incidenciaService.eliminar(incidenciaId);
	}
}
