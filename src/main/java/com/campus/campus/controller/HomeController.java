package com.campus.campus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.campus.campus.dto.UsuarioRegistroRequest;
import com.campus.campus.mapper.ApiMapper;
import com.campus.campus.model.RolUsuario;
import com.campus.campus.service.CamperService;
import com.campus.campus.service.ComputadorService;
import com.campus.campus.service.DashboardService;
import com.campus.campus.service.IncidenciaService;
import com.campus.campus.service.PrestamoService;
import com.campus.campus.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

	private final DashboardService dashboardService;
	private final CamperService camperService;
	private final ComputadorService computadorService;
	private final PrestamoService prestamoService;
	private final IncidenciaService incidenciaService;
	private final UsuarioService usuarioService;
	private final ApiMapper apiMapper;

	public HomeController(
			DashboardService dashboardService,
			CamperService camperService,
			ComputadorService computadorService,
			PrestamoService prestamoService,
			IncidenciaService incidenciaService,
			UsuarioService usuarioService,
			ApiMapper apiMapper) {
		this.dashboardService = dashboardService;
		this.camperService = camperService;
		this.computadorService = computadorService;
		this.prestamoService = prestamoService;
		this.incidenciaService = incidenciaService;
		this.usuarioService = usuarioService;
		this.apiMapper = apiMapper;
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("resumen", dashboardService.resumen());
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/registro")
	public String registro(Model model) {
		if (!model.containsAttribute("usuarioForm")) {
			model.addAttribute("usuarioForm", new UsuarioRegistroRequest("", "", "", RolUsuario.COORDINACION));
		}
		model.addAttribute("roles", RolUsuario.values());
		return "register";
	}

	@PostMapping("/registro")
	public String registrar(
			@Valid @ModelAttribute("usuarioForm") UsuarioRegistroRequest request,
			BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("roles", RolUsuario.values());
			return "register";
		}

		usuarioService.registrar(request);
		return "redirect:/login?registered";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("resumen", dashboardService.resumen());
		model.addAttribute("usuarios", usuarioService.listar().stream().map(apiMapper::toResponse).toList());
		return "dashboard";
	}

	@GetMapping("/modulos/campers")
	public String campers(Model model) {
		model.addAttribute("items", camperService.listar().stream().map(apiMapper::toResponse).toList());
		model.addAttribute("titulo", "Modulo de campers");
		model.addAttribute("descripcion", "Gestion y consulta de campers registrados.");
		return "modules/campers";
	}

	@GetMapping("/modulos/computadores")
	public String computadores(Model model) {
		model.addAttribute("items", computadorService.listar().stream().map(apiMapper::toResponse).toList());
		model.addAttribute("titulo", "Modulo de computadores");
		model.addAttribute("descripcion", "Inventario institucional de equipos y estados.");
		return "modules/computadores";
	}

	@GetMapping("/modulos/prestamos")
	public String prestamos(Model model) {
		model.addAttribute("items", prestamoService.listar().stream().map(apiMapper::toResponse).toList());
		model.addAttribute("titulo", "Modulo de prestamos");
		model.addAttribute("descripcion", "Seguimiento de asignaciones, devoluciones y cancelaciones.");
		return "modules/prestamos";
	}

	@GetMapping("/modulos/incidencias")
	public String incidencias(Model model) {
		model.addAttribute("items", incidenciaService.listar().stream().map(apiMapper::toResponse).toList());
		model.addAttribute("titulo", "Modulo de incidencias");
		model.addAttribute("descripcion", "Control de novedades tecnicas y soporte operativo.");
		return "modules/incidencias";
	}
}
