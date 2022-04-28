package br.com.cooperativa.decida.model.form;

import javax.validation.constraints.Min;

import br.com.cooperativa.decida.model.dto.SessaoVotacaoDto;
import lombok.Getter;

@Getter
public class SessaoVotacaoForm {
	@Min(1)
	private Long prazoExpiracaoEmMinutos;
	
	public SessaoVotacaoDto toDto(int idPauta) {
		return new SessaoVotacaoDto(idPauta, this.prazoExpiracaoEmMinutos);
	}
}
