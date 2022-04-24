package br.com.cooperativa.decida.exception;

public class PautaImutavelException extends Exception {
	public PautaImutavelException() {
		super("Pauta com sessão de votação não pode ser alterada");
	}
}
