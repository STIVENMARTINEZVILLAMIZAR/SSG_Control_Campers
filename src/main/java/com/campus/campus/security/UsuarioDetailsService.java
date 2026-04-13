package com.campus.campus.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.campus.campus.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository.findByCorreo(username)
				.map(usuario -> User.builder()
						.username(usuario.getCorreo())
						.password(usuario.getPassword())
						.disabled(!usuario.isActivo())
						.authorities(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con correo " + username));
	}
}
