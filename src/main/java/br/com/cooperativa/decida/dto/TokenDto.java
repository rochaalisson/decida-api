package br.com.cooperativa.decida.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {
	private String token;
	private String tipo;
}