package br.com.cooperativa.decida.model.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.cooperativa.decida.model.dto.VotoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class VotoForm {
	@NotNull @Pattern(regexp = "^(SIM|NAO)$", message = "Escolha deve ser 'SIM' ou 'NAO'")
	private String escolha;
	@NotNull @Min(1)
	private Integer idPauta;
	public VotoDto toDto(String cpfUsuarioLogado) {
		return new VotoDto(this.escolha, this.idPauta, cpfUsuarioLogado);
	}
}
