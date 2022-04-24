package br.com.cooperativa.decida.model.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.cooperativa.decida.model.dto.VotoDto;
import br.com.cooperativa.decida.model.enums.OpcaoDeVoto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class VotoForm {
	@NotNull
	private OpcaoDeVoto escolha;
	@NotNull @Min(1)
	private Integer idPauta;
	public VotoDto toDto(String cpfUsuarioLogado) {
		return new VotoDto(this.escolha, this.idPauta, cpfUsuarioLogado);
	}
}
