package br.com.cooperativa.decida.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.cooperativa.decida.dto.VotoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = 
	@UniqueConstraint(columnNames = { "usuario_id", "pauta_id" }))
public class Voto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime dataHoraVoto = LocalDateTime.now();
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('Sim', 'Nao')")
	private OpcaoDeVoto escolha;

	public Voto(VotoDto dto, SessaoVotacao sessao, Usuario usuario) {
		this.escolha = dto.getEscolha();
		this.sessao = sessao;
		this.usuario = usuario;
	}
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	@OneToOne
	@JoinColumn(name = "pauta_id")
	private SessaoVotacao sessao;
	
}
