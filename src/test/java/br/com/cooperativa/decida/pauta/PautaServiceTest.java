package br.com.cooperativa.decida.pauta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cooperativa.decida.dto.PautaDto;
import br.com.cooperativa.decida.dto.SessaoVotacaoDto;
import br.com.cooperativa.decida.modelo.Pauta;
import br.com.cooperativa.decida.modelo.SessaoVotacao;
import br.com.cooperativa.decida.repository.PautaRepository;
import br.com.cooperativa.decida.repository.SessaoVotacaoRepository;
import br.com.cooperativa.decida.service.PautaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class PautaServiceTest {
	@Autowired
	private PautaService pautaService;
	
	@MockBean(name = "repository")
	private PautaRepository repository;

	@MockBean(name = "sessaoRepository")
	private SessaoVotacaoRepository sessaoRepository;
	
	@Test
	void deveInserirPauta_ParaDadosValidos() throws Exception{
		String titulo = "Titulo inserir";
		PautaDto dto = new PautaDto(titulo, "Descricao");
		Pauta pauta = new Pauta(dto.getTitulo(), dto.getDescricao());
		when(repository.save(any(Pauta.class))).thenReturn(pauta);
		
		PautaDto dtoInserido = pautaService.cadastrar(dto);
		
		assertNotNull(dtoInserido);
		assertEquals(titulo, dto.getTitulo());
	}
	
	@Test
	void deveBuscarPautas() throws Exception{
		String titulo = "Titulo listagem";
		Pauta pauta = new Pauta(titulo, "Descricao");
		List<Pauta> pautas = Arrays.asList(pauta, pauta, pauta);
		when(repository.findAll()).thenReturn(pautas);
		
		List<PautaDto> pautasDto = pautaService.listar();
		
		assertEquals(3, pautasDto.size());
		assertEquals(titulo, pautasDto.get(0).getTitulo());
	}
	
	@Test
	void deveAbrirSessaoDeVotacao() throws Exception{
		String titulo = "Titulo abrir sessao";
		int idPauta = 1;
		long prazoExpiracao = 5;
		
		Pauta pauta = new Pauta(titulo, "Descricao");
		LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(prazoExpiracao);
		SessaoVotacao sessao = new SessaoVotacao(pauta, dataExpiracao);
		
		when(repository.findById(idPauta)).thenReturn(Optional.of(pauta));
		when(sessaoRepository.save(any(SessaoVotacao.class))).thenReturn(sessao);
		SessaoVotacaoDto sessaoDto = new SessaoVotacaoDto(idPauta, prazoExpiracao);
		
		SessaoVotacaoDto sessaoCriada = pautaService.abrirSessao(sessaoDto);
		
		assertNotNull(sessaoCriada);
		assertEquals(dataExpiracao, sessaoCriada.getDataExpiracao());
	}
}
