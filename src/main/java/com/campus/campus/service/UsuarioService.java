package com.campus.campus.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.campus.dto.UsuarioRegistroRequest;
import com.campus.campus.exception.BusinessException;
import com.campus.campus.exception.ResourceNotFoundException;
import com.campus.campus.model.Usuario;
import com.campus.campus.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly = true)
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Usuario obtenerPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con id " + id));
	}

	@Transactional(readOnly = true)
	public Usuario obtenerPorCorreo(String correo) {
		return usuarioRepository.findByCorreo(correo)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con correo " + correo));
	}

	@Transactional
	public Usuario registrar(UsuarioRegistroRequest request) {
		if (usuarioRepository.existsByCorreo(request.correo())) {
			throw new BusinessException("Ya existe un usuario registrado con ese correo.");
		}

		Usuario usuario = new Usuario();
		usuario.setNombreCompleto(request.nombreCompleto());
		usuario.setCorreo(request.correo());
		usuario.setPassword(passwordEncoder.encode(request.password()));
		usuario.setRol(request.rol());
		usuario.setActivo(true);
		return usuarioRepository.save(usuario);
	}
}
