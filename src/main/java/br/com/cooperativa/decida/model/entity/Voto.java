package br.com.cooperativa.decida.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.cooperativa.decida.model.enums.OpcaoDeVoto;
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
	
	@Enumerated(EnumType.STRING) @Column(columnDefinition = "ENUM('SIM', 'NAO')")
	private OpcaoDeVoto escolha;
	
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@OneToOne @JoinColumn(name = "pauta_id")
	private SessaoVotacao sessao;
	
	public Voto(OpcaoDeVoto escolha, SessaoVotacao sessao, Usuario usuario) {
		this.escolha = escolha;
		this.sessao = sessao;
		this.usuario = usuario;
	}
	
	
}
