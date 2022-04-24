package br.com.cooperativa.decida.model.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.cooperativa.decida.model.dto.PautaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Getter
public class PautaForm {
	@NotNull @Length(min = 5)
	private String titulo;
	private String descricao;
	
	public PautaDto toDto(Integer id) {
		return new PautaDto(id, this.titulo, this.descricao);
	}
	
	public PautaDto toDto() {
		return new PautaDto(this.titulo, this.descricao);
	}
}
