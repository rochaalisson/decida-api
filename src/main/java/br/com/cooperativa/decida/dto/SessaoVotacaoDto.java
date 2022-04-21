package br.com.cooperativa.decida.dto;

import java.time.LocalDateTime;

import br.com.cooperativa.decida.controller.form.SessaoVotacaoForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SessaoVotacaoDto {
	private Integer idPauta;
	private LocalDateTime dataExpiracao;
	
	public SessaoVotacaoDto(Integer idPauta, SessaoVotacaoForm form) {
		this.idPauta = idPauta;
		Long prazoExpiracaoMinutos = form.getPrazoExpiracaoEmMinutos(); 
		if(prazoExpiracaoMinutos != null && prazoExpiracaoMinutos != 0l)
			this.dataExpiracao = LocalDateTime.now().plusMinutes(prazoExpiracaoMinutos);
	}
}
