package br.com.cooperativa.decida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cooperativa.decida.model.entity.Pauta;
import br.com.cooperativa.decida.model.entity.Voto;

public interface VotoRepository extends JpaRepository<Voto, Integer> {
}
