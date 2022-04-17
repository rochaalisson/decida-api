package br.com.cooperativa.decida.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@AllArgsConstructor @Getter
public class Usuario {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
}
