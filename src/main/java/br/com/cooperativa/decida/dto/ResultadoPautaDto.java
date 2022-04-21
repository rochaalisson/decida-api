package br.com.cooperativa.decida.dto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.cooperativa.decida.modelo.OpcaoDeVoto;
import br.com.cooperativa.decida.modelo.Pauta;
import br.com.cooperativa.decida.modelo.SessaoVotacao;
import br.com.cooperativa.decida.modelo.Voto;
import lombok.Getter;

@Getter
public class ResultadoPautaDto {
	private String titulo;
	private String descricao;
	private Boolean isResultadoFinal;
	private OpcaoDeVoto vencedor;
	private Map<OpcaoDeVoto, Double> porcentagens;
	private Map<OpcaoDeVoto, Long> votosPorOpcao;
	private Integer quantidadeTotalVotos;
	
	public ResultadoPautaDto(Pauta pauta) {
		this.titulo = pauta.getTitulo();
		this.descricao = pauta.getDescricao();
		
		SessaoVotacao sessao = pauta.getSessaoVotacao();		
		List<Voto> votos = sessao.getVotos();
		
		this.isResultadoFinal = sessao.isExpirada();
		
		this.quantidadeTotalVotos = votos.size();
		
		this.votosPorOpcao = votos.stream()
				.collect(Collectors.groupingBy(Voto::getEscolha, Collectors.counting()));
		
		this.vencedor = Collections.max(votosPorOpcao.entrySet(), Map.Entry.comparingByValue()).getKey();
		
		this.porcentagens = votosPorOpcao.entrySet().stream()
				.collect(Collectors.toMap(
							e -> e.getKey(),
							e -> (double)e.getValue()/quantidadeTotalVotos * 100d
						));
	}
}
