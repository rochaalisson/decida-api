package br.com.cooperativa.decida.model.dto;

import br.com.cooperativa.decida.model.entity.Pauta;
import br.com.cooperativa.decida.model.entity.SessaoVotacao;
import lombok.Getter;

@Getter
public class ResultadoPautaDto {
	private String titulo;
	private String descricao;
	private ResultadoSessaoDto resultadoSessao;
	
	public ResultadoPautaDto(Pauta pauta) {
		this.titulo = pauta.getTitulo();
		this.descricao = pauta.getDescricao();
		
		SessaoVotacao sessao = pauta.getSessaoVotacao();
		this.resultadoSessao = sessao != null ? new ResultadoSessaoDto(sessao) : null;
	}
}
