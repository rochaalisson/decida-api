package br.com.cooperativa.decida.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.cooperativa.decida.dto.ErroDto;

@RestControllerAdvice
public class ConflitoDeIdHandler {
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErroDto handle(DataIntegrityViolationException exception) {
		return new ErroDto("Dados duplicados");
	}
}
