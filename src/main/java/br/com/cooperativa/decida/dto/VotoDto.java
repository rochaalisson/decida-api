package br.com.cooperativa.decida.dto;

import java.time.LocalDateTime;

import br.com.cooperativa.decida.modelo.OpcaoDeVoto;
import br.com.cooperativa.decida.modelo.SessaoVotacao;
import br.com.cooperativa.decida.modelo.Usuario;
import br.com.cooperativa.decida.modelo.Voto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VotoDto {
	private OpcaoDeVoto escolha;
	private Integer idPauta;
	private LocalDateTime dataHoraVoto;
	private String cpfUsuario;
	
	public Voto toEntity(SessaoVotacao sessaoVotacao, Usuario usuario) {
		return new Voto(this.escolha, sessaoVotacao, usuario);
	}
	
	public VotoDto(OpcaoDeVoto escolha, Integer idPauta, String cpfUsuario) {
		this.escolha = escolha;
		this.idPauta = idPauta;
		this.cpfUsuario = cpfUsuario;
	}

	public VotoDto(Voto voto) {
		this.escolha = voto.getEscolha();
		this.idPauta = voto.getSessao().getIdPauta();
		this.dataHoraVoto = voto.getDataHoraVoto();
		this.cpfUsuario = voto.getUsuario().getCpf();
	}
}
