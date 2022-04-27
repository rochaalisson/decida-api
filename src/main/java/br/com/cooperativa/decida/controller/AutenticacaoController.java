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

import br.com.cooperativa.decida.model.dto.TokenDto;
import br.com.cooperativa.decida.model.form.LoginForm;
import br.com.cooperativa.decida.service.TokenService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	private AuthenticationManager authManager;
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> login(@RequestBody LoginForm form) {
		UsernamePasswordAuthenticationToken credentials = form.toAuthenticationToken();
		try {
			Authentication authentication = authManager.authenticate(credentials);
			
			TokenDto token = new TokenDto(tokenService.gerarToken(authentication), "Bearer");
			return ResponseEntity.ok(token);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
