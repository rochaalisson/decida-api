package br.com.cooperativa.decida.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import br.com.cooperativa.decida.dto.PautaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Pauta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String titulo;
	private String descricao;

	@ManyToOne
	private Usuario usuario;
	@OneToOne
	@PrimaryKeyJoinColumn
	private SessaoVotacao sessaoVotacao;
	
	public Pauta(String titulo, String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
	}

	public void atualizarDados(PautaDto dto) {
		this.titulo = dto.getTitulo();
		this.descricao = dto.getDescricao();
	}
	
}
