package br.com.cooperativa.decida.pauta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import br.com.cooperativa.decida.exception.PautaImutavelException;
import br.com.cooperativa.decida.exception.UsuarioNaoAutorizadoException;
import br.com.cooperativa.decida.model.dto.PautaDto;
import br.com.cooperativa.decida.model.dto.SessaoVotacaoDto;
import br.com.cooperativa.decida.model.entity.Pauta;
import br.com.cooperativa.decida.model.entity.SessaoVotacao;
import br.com.cooperativa.decida.model.entity.Usuario;
import br.com.cooperativa.decida.repository.PautaRepository;
import br.com.cooperativa.decida.repository.SessaoVotacaoRepository;
import br.com.cooperativa.decida.repository.UsuarioRepository;
import br.com.cooperativa.decida.service.PautaService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PautaServiceTest {
	private PautaService pautaService;

	@MockBean
	private PautaRepository repository;
	@MockBean
	private SessaoVotacaoRepository sessaoRepository;
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	public PautaServiceTest() {
		this.repository = mock(PautaRepository.class);
		this.sessaoRepository = mock(SessaoVotacaoRepository.class);
		this.usuarioRepository = mock(UsuarioRepository.class);
		
		this.pautaService = new PautaService(repository, sessaoRepository, usuarioRepository);
	}
	
	private final String cpfPadrao = "12345678901";
	private Usuario novoUsuario() {
		return new Usuario("usuario@email.com", cpfPadrao);
	}
	
	@Test
	void deveInserirPauta_ParaDadosValidos() throws Exception{
		String titulo = "Titulo inserir";
		PautaDto dto = new PautaDto(titulo, "Descricao");
		Usuario usuario = novoUsuario();
		Pauta pauta = dto.toEntity(usuario);
		when(repository.save(any(Pauta.class))).thenReturn(pauta);
		when(usuarioRepository.findByCpf(cpfPadrao)).thenReturn(Optional.of(usuario));
		
		PautaDto dtoInserido = pautaService.cadastrar(dto, cpfPadrao);
		
		assertNotNull(dtoInserido);
		assertEquals(titulo, dto.getTitulo());
	}
	
	@Test
	void deveBuscarPautas() throws Exception{
		String titulo = "Titulo listagem";
		Usuario usuario = novoUsuario();

		Pauta pauta = new Pauta(titulo, "Descricao", usuario);
		List<Pauta> pautas = Arrays.asList(pauta, pauta, pauta);
		when(repository.findAll(any(Specification.class))).thenReturn(pautas);

		List<PautaDto> pautasDto = pautaService.listar(Optional.empty(), Optional.empty());
		
		assertEquals(3, pautasDto.size());
		assertEquals(titulo, pautasDto.get(0).getTitulo());
	}
	
	@Test
	void deveBuscarPautas_comFiltroTitulo() throws Exception{
		String titulo = "Titulo listagem";
		String buscaTitulo = "list";
		Usuario usuario = novoUsuario();

		Pauta pauta = new Pauta(titulo, "Descricao", usuario);
		Pauta pautaDiferente = new Pauta("SemFiltro", "Descricao", usuario);
		List<Pauta> pautas = Arrays.asList(pauta, pauta, pautaDiferente);
		List<Pauta> pautasFiltradas = pautas.stream()
				.filter(p -> p.getTitulo().contains(buscaTitulo)).collect(Collectors.toList());
		when(repository.findAll(any(Specification.class))).thenReturn(pautasFiltradas);

		List<PautaDto> pautasDto = pautaService.listar(Optional.of(buscaTitulo), Optional.empty());
		
		assertEquals(2, pautasDto.size());
		assertTrue(pautasDto.stream().allMatch(p -> p.getTitulo().contains(buscaTitulo)));
	}

	@Test
	void deveBuscarPautas_comFiltroDescricao() throws Exception{
		String descricao = "Descricao listagem";
		String buscaDescricao = "list";
		Usuario usuario = novoUsuario();

		Pauta pauta = new Pauta("titulo", descricao, usuario);
		Pauta pautaDiferente = new Pauta("SemFiltro", "semfiltro", usuario);
		List<Pauta> pautas = Arrays.asList(pauta, pauta, pauta, pautaDiferente);
		List<Pauta> pautasFiltradas = pautas.stream()
				.filter(p -> p.getDescricao().contains(buscaDescricao)).collect(Collectors.toList());
		when(repository.findAll(any(Specification.class))).thenReturn(pautasFiltradas);

		List<PautaDto> pautasDto = pautaService.listar(Optional.empty(), Optional.of(buscaDescricao));
		
		assertEquals(3, pautasDto.size());
		assertTrue(pautasDto.stream().allMatch(p -> p.getDescricao().contains(buscaDescricao)));
	}
	
	@Test
	void deveAbrirSessaoDeVotacao() throws Exception{
		String titulo = "Titulo abrir sessao";
		int idPauta = 1;
		long prazoExpiracao = 5;
		
		Usuario usuario = novoUsuario();
		Pauta pauta = new Pauta(titulo, "Descricao", usuario);
		LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(prazoExpiracao);
		SessaoVotacao sessao = new SessaoVotacao(pauta, dataExpiracao);
		
		when(repository.findById(idPauta)).thenReturn(Optional.of(pauta));
		when(sessaoRepository.save(any(SessaoVotacao.class))).thenReturn(sessao);
		when(usuarioRepository.findByCpf(cpfPadrao)).thenReturn(Optional.of(usuario));

		SessaoVotacaoDto sessaoDto = new SessaoVotacaoDto(idPauta, prazoExpiracao);
		
		SessaoVotacaoDto sessaoCriada = pautaService.abrirSessao(sessaoDto, cpfPadrao);
		
		assertNotNull(sessaoCriada);
		assertEquals(dataExpiracao, sessaoCriada.getDataExpiracao());
	}
	
	@Test
	void deveAtualizarPauta() throws Exception {
		String tituloOriginal = "Titulo original";
		String tituloAtualizado = "Titulo atualizado";
		int idPauta = 1;
		
		Usuario usuario = novoUsuario();
		Pauta pautaOriginal = new Pauta(tituloOriginal, "Descricao atualizar", usuario);
		when(repository.findById(idPauta)).thenReturn(Optional.of(pautaOriginal));
		when(usuarioRepository.findByCpf(cpfPadrao)).thenReturn(Optional.of(usuario));
		
		PautaDto dto = new PautaDto(idPauta, tituloAtualizado, "Descricao");
		pautaService.atualizar(dto, cpfPadrao);

		assertEquals(tituloAtualizado, dto.getTitulo());
	}

	@Test
	void deveLancarErro_aoAtualizarPautaComUsuarioNaoAutorizado() throws Exception {
		int idPauta = 1;
		String cpfNaoAutorizado = "09876543211";
		
		Usuario usuario = novoUsuario();
		Pauta pauta = new Pauta("titulo", "Descricao atualizar", usuario);
		when(repository.findById(idPauta)).thenReturn(Optional.of(pauta));
		
		PautaDto dto = new PautaDto(idPauta, "titulo", "Descricao");
		assertThrows(UsuarioNaoAutorizadoException.class, () -> pautaService.atualizar(dto, cpfNaoAutorizado));
	}

	@Test
	void deveLancarErro_aoAtualizarPautaComSessaoDeVotacao() throws Exception {
		int idPauta = 1;
		
		Usuario usuario = novoUsuario();
		Pauta pauta = new Pauta("titulo", "Descricao atualizar", usuario);
		SessaoVotacao sessao = new SessaoVotacao(pauta, LocalDateTime.now());
		pauta.setSessaoVotacao(sessao);
		when(repository.findById(idPauta)).thenReturn(Optional.of(pauta));
		
		PautaDto dto = new PautaDto(idPauta, "titulo", "Descricao");
		assertThrows(PautaImutavelException.class, () -> pautaService.atualizar(dto, cpfPadrao));
	}
	
	@Test
	void deletarPauta_deveFalharParaPautaNaoEncontrada() throws Exception {
		int idInexistente = -1;
		
		when(repository.findById(idInexistente)).thenReturn(Optional.empty());
		
		
		assertThrows(EntityNotFoundException.class, () -> pautaService.deletar(idInexistente, cpfPadrao));
	}
	
	@Test
	void deletarPauta_deveFalharParaUsuarioNaoAutorizado() throws Exception {
		int idPauta = 1;
		String cpfNaoAutorizado = "1234";
		
		Usuario usuario = novoUsuario();
		Pauta pauta = new Pauta("titulo", "descricao", usuario);
		when(repository.findById(idPauta)).thenReturn(Optional.of(pauta));
		
		assertThrows(UsuarioNaoAutorizadoException.class, () -> pautaService.deletar(idPauta, cpfNaoAutorizado));
	}
	

	@Test
	void deveLancarErro_aoDeletarPautaComSessaoDeVotacao() throws Exception {
		int idPauta = 1;
		
		Usuario usuario = novoUsuario();
		Pauta pauta = new Pauta("titulo", "Descricao deletar", usuario);
		SessaoVotacao sessao = new SessaoVotacao(pauta, LocalDateTime.now());
		pauta.setSessaoVotacao(sessao);
		when(repository.findById(idPauta)).thenReturn(Optional.of(pauta));
		
		PautaDto dto = new PautaDto(idPauta, "titulo", "Descricao");
		assertThrows(PautaImutavelException.class, () -> pautaService.atualizar(dto, cpfPadrao));
	}
}
