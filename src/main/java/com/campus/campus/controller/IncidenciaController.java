package com.campus.campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campus.campus.dto.IncidenciaRequest;
import com.campus.campus.model.Incidencia;
import com.campus.campus.service.IncidenciaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

	private final IncidenciaService incidenciaService;

	public IncidenciaController(IncidenciaService incidenciaService) {
		this.incidenciaService = incidenciaService;
	}

	@GetMapping
	public List<Incidencia> listar() {
		return incidenciaService.listar();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Incidencia crear(@Valid @RequestBody IncidenciaRequest request) {
		return incidenciaService.crear(request);
	}
}
