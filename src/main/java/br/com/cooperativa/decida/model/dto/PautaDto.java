package br.com.cooperativa.decida.model.dto;

import br.com.cooperativa.decida.model.entity.Pauta;
import br.com.cooperativa.decida.model.entity.Usuario;
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
	
	public Pauta toEntity(Usuario usuario) {
		return new Pauta(titulo, descricao, usuario);
	}
}
