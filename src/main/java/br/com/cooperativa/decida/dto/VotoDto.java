package br.com.cooperativa.decida.dto;

import java.time.LocalDateTime;

import br.com.cooperativa.decida.controller.form.VotoForm;
import br.com.cooperativa.decida.modelo.OpcaoDeVoto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VotoDto {
	private OpcaoDeVoto escolha;
	private Integer idPauta;
	private LocalDateTime dataHoraVoto;
	private String cpfUsuario;
	
	public VotoDto(VotoForm form, String cpfUsuario) {
		this.escolha = form.getEscolha();
		this.idPauta = form.getIdPauta();
		this.cpfUsuario = cpfUsuario;
	}
}
