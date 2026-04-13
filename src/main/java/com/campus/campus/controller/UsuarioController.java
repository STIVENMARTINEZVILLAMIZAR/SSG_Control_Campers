package com.campus.campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campus.campus.dto.UsuarioRegistroRequest;
import com.campus.campus.dto.UsuarioResponse;
import com.campus.campus.mapper.ApiMapper;
import com.campus.campus.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	private final ApiMapper apiMapper;

	public UsuarioController(UsuarioService usuarioService, ApiMapper apiMapper) {
		this.usuarioService = usuarioService;
		this.apiMapper = apiMapper;
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<UsuarioResponse> listar() {
		return usuarioService.listar().stream().map(apiMapper::toResponse).toList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('ADMIN')")
	public UsuarioResponse registrar(@Valid @RequestBody UsuarioRegistroRequest request) {
		return apiMapper.toResponse(usuarioService.registrar(request));
	}
}
