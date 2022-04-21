package br.com.cooperativa.decida.controller;

import static br.com.cooperativa.decida.util.ConversorDeObjeto.converter;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cooperativa.decida.controller.form.VotoForm;
import br.com.cooperativa.decida.dto.VotoDto;
import br.com.cooperativa.decida.service.VotoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/votos")
@RequiredArgsConstructor
public class VotoController {
	private final VotoService votoService;
	
	@PostMapping
	public ResponseEntity<VotoDto> votar(@RequestBody @Valid VotoForm form, UriComponentsBuilder uriBuilder) throws Exception {
		VotoDto voto = converter(form, VotoDto.class);
		voto = votoService.votar(voto);
		
		URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(voto.getIdPauta()).toUri();
		return ResponseEntity.created(uri).body(voto);
	}
}
