package br.com.cooperativa.decida.modelo;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.cooperativa.decida.dto.SessaoVotacaoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SessaoVotacao {
	@Id
	private Integer idPauta;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	private LocalDateTime dataExpiracao = dataCriacao.plusMinutes(1);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sessao")
	private List<Voto> votos;
	@OneToOne
	@MapsId
	private Pauta pauta;
	
	public SessaoVotacao(SessaoVotacaoDto dto, Pauta pauta) {
		this.pauta = pauta;
		if(dto.getDataExpiracao() != null)
			this.dataExpiracao = dto.getDataExpiracao();
	}
	
	public Boolean isExpirada() {
		return LocalDateTime.now().isAfter(dataExpiracao);
	}
}
