package br.com.cooperativa.decida.exception;

public class SessaoVotacaoExpiradaException extends Exception {
	public SessaoVotacaoExpiradaException() {
		super("Sessão de votação expirada");
	}
}
