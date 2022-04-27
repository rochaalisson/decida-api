package br.com.cooperativa.decida.controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cooperativa.decida.exception.UsuarioNaoAutorizadoException;
import br.com.cooperativa.decida.model.dto.PautaDto;
import br.com.cooperativa.decida.model.dto.ResultadoPautaDto;
import br.com.cooperativa.decida.model.dto.SessaoVotacaoDto;
import br.com.cooperativa.decida.model.form.PautaForm;
import br.com.cooperativa.decida.model.form.SessaoVotacaoForm;
import br.com.cooperativa.decida.service.PautaService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pautas")
@AllArgsConstructor
public class PautaController {
	private PautaService pautaService;
	
	@PostMapping
	public ResponseEntity<PautaDto> cadastrar(@RequestBody @Valid PautaForm form, Principal principal, UriComponentsBuilder uriBuilder) {
		PautaDto pauta = form.toDto();
		String cpfUsuario = principal.getName();
		pauta = pautaService.cadastrar(pauta, cpfUsuario);
		
		URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(pauta.getId()).toUri();
		return ResponseEntity.created(uri).body(pauta);
	}
	
	@GetMapping
	public List<PautaDto> listar(@RequestParam Optional<String> titulo,
			@RequestParam Optional<String> descricao) {
		return pautaService.listar(titulo, descricao);
	}
	
	@GetMapping("/{id}")
	public ResultadoPautaDto obterResultado(@PathVariable Integer id) {
		return pautaService.obterResultado(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Integer id, Principal principal) throws Exception {
		String cpfUsuarioLogado = principal.getName();
		
		pautaService.deletar(id, cpfUsuarioLogado);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PautaDto> atualizar(@PathVariable Integer id, @RequestBody @Valid PautaForm form, Principal principal) throws Exception {
		String cpfUsuarioLogado = principal.getName();
		
		PautaDto pauta = form.toDto(id);
		pauta = pautaService.atualizar(pauta, cpfUsuarioLogado);
		
		return ResponseEntity.ok(pauta);
	}
	
	@PostMapping("/{idPauta}/abrirSessao")
	public ResponseEntity<SessaoVotacaoDto> abrirSessao(@RequestBody @Valid SessaoVotacaoForm form,
			@PathVariable Integer idPauta, Principal principal, UriComponentsBuilder uriBuilder) throws UsuarioNaoAutorizadoException {
		SessaoVotacaoDto sessao = form.toDto(idPauta);
		String cpfUsuario = principal.getName(); 
		
		sessao = pautaService.abrirSessao(sessao, cpfUsuario);
		
		return ResponseEntity.ok(sessao);
	}
}
