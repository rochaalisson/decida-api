package br.com.cooperativa.decida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.cooperativa.decida.model.entity.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Integer>, JpaSpecificationExecutor<Pauta> {
}
