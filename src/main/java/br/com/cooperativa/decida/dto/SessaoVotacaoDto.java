package br.com.cooperativa.decida.dto;

import java.time.LocalDateTime;

import br.com.cooperativa.decida.modelo.Pauta;
import br.com.cooperativa.decida.modelo.SessaoVotacao;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SessaoVotacaoDto {
	private Integer idPauta;
	private LocalDateTime dataExpiracao;
	
	public SessaoVotacaoDto(Integer idPauta, Long prazoExpiracaoMinutos) {
		this.idPauta = idPauta; 
		if(prazoExpiracaoMinutos != null && prazoExpiracaoMinutos != 0l)
			this.dataExpiracao = LocalDateTime.now().plusMinutes(prazoExpiracaoMinutos);
	}
	
	public SessaoVotacaoDto(SessaoVotacao sessaoVotacao) {
		this.idPauta = sessaoVotacao.getIdPauta();
		this.dataExpiracao = sessaoVotacao.getDataExpiracao();
	}
	
	public SessaoVotacao toEntity(Pauta pauta) {
		return new SessaoVotacao(pauta, dataExpiracao);
	}
	
}
