package com.aeon.loginjwt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.loginjwt.entity.Usuario;
import com.aeon.loginjwt.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		String password = usuario.getPassword();
		String code = encoder.encode(password);
		usuario.setPassword(code);

		return ResponseEntity.ok(repository.save(usuario));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> listarTodos() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/validar")
	public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password) {
		
		Optional<Usuario> optUsuario = repository.findByLogin(login);
		if (optUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		
		Usuario usuario = optUsuario.get();
		boolean valid = encoder.matches(password, usuario.getPassword());
		
		HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED; 
		
		return ResponseEntity.status(status).body(valid);
	}
}
