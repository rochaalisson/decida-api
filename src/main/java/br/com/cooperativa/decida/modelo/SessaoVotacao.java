package br.com.cooperativa.decida.modelo;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.cooperativa.decida.dto.SessaoVotacaoDto;
import lombok.Getter;

@Entity
@Getter
public class SessaoVotacao {
	@Id
	private Integer idPauta;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	private Long prazoExpiracaoMinutos = 1l;
	
	@OneToMany
	private List<Voto> votos;
	@OneToOne
	@MapsId
	private Pauta pauta;
	
	public SessaoVotacao(SessaoVotacaoDto dto, Pauta pauta) {
		this.pauta = pauta;
		if(dto.getPrazoExpiracaoMinutos() != null)
			this.prazoExpiracaoMinutos = dto.getPrazoExpiracaoMinutos();
	}
	
	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}
	
	public void setPrazoExpiracao(Long prazoExpiracao) {
		this.prazoExpiracaoMinutos = prazoExpiracao;
	}
	
}
