package br.com.cooperativa.decida.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@AllArgsConstructor @Getter
public class SessaoVotacao {
	private Integer id;
	private List<Voto> votos;
	@OneToOne
	private Pauta pauta;
}
