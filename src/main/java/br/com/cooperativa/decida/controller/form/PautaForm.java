package br.com.cooperativa.decida.controller.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;

@Getter
public class PautaForm {
	@NotNull @Length(min = 5)
	private String titulo;
	private String descricao;
}
