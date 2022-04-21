package br.com.cooperativa.decida.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroFormularioDto {
	private String campo;
	private String erro;
}
