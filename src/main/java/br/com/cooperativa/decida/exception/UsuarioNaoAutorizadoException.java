package br.com.cooperativa.decida.exception;

public class UsuarioNaoAutorizadoException extends Exception {
	public UsuarioNaoAutorizadoException() {
		super("Usuário não autorizado");
	}
}
