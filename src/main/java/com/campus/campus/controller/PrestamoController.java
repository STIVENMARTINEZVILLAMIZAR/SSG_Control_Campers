package com.campus.campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campus.campus.dto.DevolucionRequest;
import com.campus.campus.dto.PrestamoRequest;
import com.campus.campus.model.PrestamoComputador;
import com.campus.campus.service.PrestamoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

	private final PrestamoService prestamoService;

	public PrestamoController(PrestamoService prestamoService) {
		this.prestamoService = prestamoService;
	}

	@GetMapping
	public List<PrestamoComputador> listar() {
		return prestamoService.listar();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PrestamoComputador crear(@Valid @RequestBody PrestamoRequest request) {
		return prestamoService.crear(request);
	}

	@PatchMapping("/{prestamoId}/devolucion")
	public PrestamoComputador devolver(
			@PathVariable Long prestamoId,
			@Valid @RequestBody DevolucionRequest request) {
		return prestamoService.devolver(prestamoId, request);
	}
}
