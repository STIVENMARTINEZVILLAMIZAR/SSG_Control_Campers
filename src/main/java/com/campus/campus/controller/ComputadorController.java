package com.campus.campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campus.campus.dto.ComputadorRequest;
import com.campus.campus.model.Computador;
import com.campus.campus.service.ComputadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/computadores")
public class ComputadorController {

	private final ComputadorService computadorService;

	public ComputadorController(ComputadorService computadorService) {
		this.computadorService = computadorService;
	}

	@GetMapping
	public List<Computador> listar() {
		return computadorService.listar();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Computador crear(@Valid @RequestBody ComputadorRequest request) {
		return computadorService.crear(request);
	}
}
