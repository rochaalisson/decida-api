package br.com.cooperativa.decida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cooperativa.decida.modelo.SessaoVotacao;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Integer> {
}
