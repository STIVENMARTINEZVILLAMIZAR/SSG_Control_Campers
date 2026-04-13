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

import com.campus.campus.dto.ComputadorRequest;
import com.campus.campus.dto.ComputadorResponse;
import com.campus.campus.dto.ComputadorUpdateRequest;
import com.campus.campus.mapper.ApiMapper;
import com.campus.campus.service.ComputadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/computadores")
public class ComputadorController {

	private final ComputadorService computadorService;
	private final ApiMapper apiMapper;

	public ComputadorController(ComputadorService computadorService, ApiMapper apiMapper) {
		this.computadorService = computadorService;
		this.apiMapper = apiMapper;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION','SOPORTE')")
	public List<ComputadorResponse> listar() {
		return computadorService.listar().stream().map(apiMapper::toResponse).toList();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION','SOPORTE')")
	public ComputadorResponse obtenerPorId(@PathVariable Long id) {
		return apiMapper.toResponse(computadorService.obtenerPorId(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole('ADMIN','SOPORTE')")
	public ComputadorResponse crear(@Valid @RequestBody ComputadorRequest request) {
		return apiMapper.toResponse(computadorService.crear(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','SOPORTE')")
	public ComputadorResponse actualizar(@PathVariable Long id, @Valid @RequestBody ComputadorUpdateRequest request) {
		return apiMapper.toResponse(computadorService.actualizar(id, request));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public void eliminar(@PathVariable Long id) {
		computadorService.eliminar(id);
	}
}
