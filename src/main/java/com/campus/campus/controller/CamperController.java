package com.campus.campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campus.campus.dto.CamperRequest;
import com.campus.campus.dto.CamperResponse;
import com.campus.campus.dto.CamperUpdateRequest;
import com.campus.campus.mapper.ApiMapper;
import com.campus.campus.service.CamperService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/campers")
public class CamperController {

	private final CamperService camperService;
	private final ApiMapper apiMapper;

	public CamperController(CamperService camperService, ApiMapper apiMapper) {
		this.camperService = camperService;
		this.apiMapper = apiMapper;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION','SOPORTE')")
	public List<CamperResponse> listar() {
		return camperService.listar().stream().map(apiMapper::toResponse).toList();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION','SOPORTE')")
	public CamperResponse obtenerPorId(@PathVariable Long id) {
		return apiMapper.toResponse(camperService.obtenerPorId(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION')")
	public CamperResponse crear(@Valid @RequestBody CamperRequest request) {
		return apiMapper.toResponse(camperService.crear(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','COORDINACION')")
	public CamperResponse actualizar(@PathVariable Long id, @Valid @RequestBody CamperUpdateRequest request) {
		return apiMapper.toResponse(camperService.actualizar(id, request));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public void eliminar(@PathVariable Long id) {
		camperService.eliminar(id);
	}
}
