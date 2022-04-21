package br.com.cooperativa.decida.config.validacao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroFormularioDto {
	private String campo;
	private String erro;
}
