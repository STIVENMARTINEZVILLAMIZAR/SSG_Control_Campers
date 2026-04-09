package com.campus.campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campus.campus.dto.CamperRequest;
import com.campus.campus.model.Camper;
import com.campus.campus.service.CamperService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/campers")
public class CamperController {

	private final CamperService camperService;

	public CamperController(CamperService camperService) {
		this.camperService = camperService;
	}

	@GetMapping
	public List<Camper> listar() {
		return camperService.listar();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Camper crear(@Valid @RequestBody CamperRequest request) {
		return camperService.crear(request);
	}
}
