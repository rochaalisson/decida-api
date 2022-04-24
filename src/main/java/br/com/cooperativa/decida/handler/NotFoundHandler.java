package br.com.cooperativa.decida.handler;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.cooperativa.decida.model.dto.ErroDto;

@RestControllerAdvice
public class NotFoundHandler {
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ErroDto handle(EntityNotFoundException exception) {
		return new ErroDto(exception.getLocalizedMessage());
	}
}
