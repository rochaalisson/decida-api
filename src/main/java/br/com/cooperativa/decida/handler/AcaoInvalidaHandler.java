package br.com.cooperativa.decida.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.cooperativa.decida.exception.PautaImutavelException;
import br.com.cooperativa.decida.exception.SessaoVotacaoExpiradaException;
import br.com.cooperativa.decida.exception.UsuarioNaoAutorizadoException;
import br.com.cooperativa.decida.model.dto.ErroDto;

@RestControllerAdvice
public class AcaoInvalidaHandler {
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(SessaoVotacaoExpiradaException.class)
	public ErroDto handleSessaoExpirada(SessaoVotacaoExpiradaException exception) {
		return new ErroDto(exception.getLocalizedMessage());
	}
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UsuarioNaoAutorizadoException.class)
	public ErroDto handleUsuarioNaoAutorizado(UsuarioNaoAutorizadoException exception) {
		return new ErroDto(exception.getLocalizedMessage());
	}
	
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErroDto handle(DataIntegrityViolationException exception) {
		return new ErroDto("Dados duplicados");
	}
	
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(PautaImutavelException.class)
	public ErroDto handlePautaImutavel(PautaImutavelException exception) {
		return new ErroDto(exception.getLocalizedMessage());
	}
}
