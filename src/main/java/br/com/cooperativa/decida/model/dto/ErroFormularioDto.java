package br.com.cooperativa.decida.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroFormularioDto {
	private String campo;
	private String erro;
}
