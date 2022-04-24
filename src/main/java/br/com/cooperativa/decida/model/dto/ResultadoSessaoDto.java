package br.com.cooperativa.decida.model.dto;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.cooperativa.decida.model.entity.SessaoVotacao;
import br.com.cooperativa.decida.model.entity.Voto;
import br.com.cooperativa.decida.model.enums.OpcaoDeVoto;
import lombok.Getter;

@Getter
public class ResultadoSessaoDto {
	private Boolean isResultadoFinal;
	private OpcaoDeVoto vencedor;
	private Map<OpcaoDeVoto, Double> porcentagens;
	private Map<OpcaoDeVoto, Long> votosPorOpcao;
	private Integer quantidadeTotalVotos;

	public ResultadoSessaoDto(SessaoVotacao sessao) {		
		List<Voto> votos = sessao.getVotos();

		this.isResultadoFinal = sessao.isExpirada();

		this.quantidadeTotalVotos = votos.size();

		this.votosPorOpcao = votos.stream().collect(Collectors.groupingBy(Voto::getEscolha, Collectors.counting()));
		
		Optional<Entry<OpcaoDeVoto, Long>> entryVencedor = votosPorOpcao.entrySet().stream()
				.max(Map.Entry.comparingByValue());
		this.vencedor = entryVencedor.isPresent() ? entryVencedor.get().getKey() : null;

		this.porcentagens = votosPorOpcao.entrySet().stream()
				.collect(Collectors.toMap(
						e -> e.getKey(),
						e -> (double) e.getValue() / quantidadeTotalVotos * 100d
						));
	}
}
