package br.com.cooperativa.decida.dto;

import br.com.cooperativa.decida.controller.form.SessaoVotacaoForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SessaoVotacaoDto {
	private Integer idPauta;
	private Long prazoExpiracaoMinutos;
	
	public SessaoVotacaoDto(Integer idPauta, SessaoVotacaoForm form) {
		this.idPauta = idPauta;
		this.prazoExpiracaoMinutos = form.getPrazoExpiracaoMinutos();
	}
}
