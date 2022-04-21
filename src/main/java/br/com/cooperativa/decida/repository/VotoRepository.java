package br.com.cooperativa.decida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cooperativa.decida.modelo.Pauta;
import br.com.cooperativa.decida.modelo.Voto;

public interface VotoRepository extends JpaRepository<Voto, Integer> {
}
