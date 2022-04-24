package br.com.cooperativa.decida.dto;

import br.com.cooperativa.decida.modelo.Pauta;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class PautaDto {
	private String titulo;
	private String descricao;
	private Integer id;
	
	public PautaDto(Integer id, String titulo, String descricao) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public PautaDto(String titulo, String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public PautaDto(Pauta pauta) {
		this.titulo = pauta.getTitulo();
		this.descricao = pauta.getDescricao();
		this.id = pauta.getId();
	}
	
	public Pauta toEntity() {
		return new Pauta(titulo, descricao);
	}
}
