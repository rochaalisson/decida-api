package br.com.cooperativa.decida.controller;

import static br.com.cooperativa.decida.util.ConversorDeObjeto.converter;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cooperativa.decida.controller.form.PautaForm;
import br.com.cooperativa.decida.controller.form.SessaoVotacaoForm;
import br.com.cooperativa.decida.dto.PautaDto;
import br.com.cooperativa.decida.dto.ResultadoPautaDto;
import br.com.cooperativa.decida.dto.SessaoVotacaoDto;
import br.com.cooperativa.decida.service.PautaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pautas")
@RequiredArgsConstructor
public class PautaController {
	private final PautaService pautaService;
	
	@PostMapping
	public ResponseEntity<PautaDto> cadastrar(@RequestBody @Valid PautaForm form, UriComponentsBuilder uriBuilder) {
		PautaDto pauta = converter(form, PautaDto.class);
		pauta = pautaService.cadastrar(pauta);
		
		URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
		return ResponseEntity.created(uri).body(pauta);
	}
	
	@GetMapping
	public List<PautaDto> listar() {
		List<PautaDto> pautas = pautaService.listar();
		
		return pautas;
	}
	
	@GetMapping("/{id}")
	public ResultadoPautaDto obterResultado(@PathVariable Integer id) throws Exception {
		ResultadoPautaDto resultado = pautaService.obterResultado(id);
		
		return resultado;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Integer id) {
		pautaService.deletar(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/{idPauta}/abrirSessao")
	public ResponseEntity<SessaoVotacaoDto> abrirSessao(@RequestBody @Valid SessaoVotacaoForm form, @PathVariable Integer idPauta, UriComponentsBuilder uriBuilder) {
		SessaoVotacaoDto sessao = new SessaoVotacaoDto(idPauta, form);
		sessao = pautaService.abrirSessao(sessao);
		
		return ResponseEntity.ok(sessao);
	}
}
