package br.com.cooperativa.decida.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.cooperativa.decida.dto.ErroDto;
import br.com.cooperativa.decida.exception.SessaoExpiradaException;

@RestControllerAdvice
public class ErroSessaoExpiradaHandler {
	@ResponseStatus(value = HttpStatus.GONE)
	@ExceptionHandler(SessaoExpiradaException.class)
	public ErroDto handle(SessaoExpiradaException exception) {
		return new ErroDto(exception.getLocalizedMessage());
	}
}
