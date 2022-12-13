package com.aeon.loginjwt.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.aeon.loginjwt.data.DetalheUsuarioData;
import com.aeon.loginjwt.entity.Usuario;
import com.aeon.loginjwt.repository.UsuarioRepository;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optUsuario = repository.findByLogin(username);
		
		if (optUsuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado"); 
		}
		
		return new DetalheUsuarioData(optUsuario);
	}
}
