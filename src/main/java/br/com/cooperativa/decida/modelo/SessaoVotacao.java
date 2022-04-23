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
	@OneToOne(fetch = FetchType.EAGER)
	@MapsId
	private Pauta pauta;

	public SessaoVotacao(Pauta pauta, LocalDateTime dataExpiracao) {
		this.pauta = pauta;
		if (dataExpiracao != null)
			this.dataExpiracao = dataExpiracao;
	}

	public Boolean isExpirada() {
		return LocalDateTime.now().isAfter(dataExpiracao);
	}
}
