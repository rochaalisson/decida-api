package br.com.cooperativa.decida.controller.form;

import javax.validation.constraints.Min;

import lombok.Getter;

@Getter
public class SessaoVotacaoForm {
	@Min(1)
	private Long prazoExpiracaoMinutos;
}
