package br.com.cooperativa.decida.modelo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter
public class Voto {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime dataHoraVoto;
	@Enumerated(EnumType.STRING)
	private OpcaoDeVoto escolha;
	
	@ManyToOne
	private Usuario usuario;
	@OneToOne
	private SessaoVotacao pauta;
}
