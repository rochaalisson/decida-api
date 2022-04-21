package br.com.cooperativa.decida.exception;

public class SessaoExpiradaException extends Exception {
	public SessaoExpiradaException() {
		super("Sessão de votação expirada");
	}
}
