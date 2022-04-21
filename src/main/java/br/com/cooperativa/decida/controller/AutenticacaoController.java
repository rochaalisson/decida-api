package br.com.cooperativa.decida.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperativa.decida.controller.form.LoginForm;
import br.com.cooperativa.decida.service.TokenService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	private final AuthenticationManager authManager;
	private final TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<String> login(@RequestBody LoginForm form) {
		UsernamePasswordAuthenticationToken credentials = form.toAuthenticationToken();
		try {
			Authentication authentication = authManager.authenticate(credentials);
			
			return ResponseEntity.ok(tokenService.gerarToken(authentication));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
