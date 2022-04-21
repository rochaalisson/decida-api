package br.com.cooperativa.decida.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;

@Getter
public class LoginForm {
	private String cpf;
	private String senha;
	
	public UsernamePasswordAuthenticationToken toAuthenticationToken() {
		return new UsernamePasswordAuthenticationToken(cpf, senha);
	}
}
