package br.com.cooperativa.decida.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cooperativa.decida.model.dto.VotoDto;
import br.com.cooperativa.decida.model.form.VotoForm;
import br.com.cooperativa.decida.service.VotoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/votos")
@RequiredArgsConstructor
public class VotoController {
	private final VotoService votoService;
	
	@PostMapping
	public ResponseEntity<VotoDto> votar(@RequestBody @Valid VotoForm form, Principal principal) throws Exception {
		String cpfUsuarioLogado = principal.getName();
		VotoDto voto = form.toDto(cpfUsuarioLogado);
		voto = votoService.votar(voto);
		
		return ResponseEntity.ok(voto);
	}
}
