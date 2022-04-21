package br.com.cooperativa.decida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cooperativa.decida.modelo.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Integer> {
}
