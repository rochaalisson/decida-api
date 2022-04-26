package br.com.cooperativa.decida.voto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cooperativa.decida.model.dto.PautaDto;
import br.com.cooperativa.decida.model.dto.SessaoVotacaoDto;
import br.com.cooperativa.decida.model.dto.VotoDto;
import br.com.cooperativa.decida.model.entity.Pauta;
import br.com.cooperativa.decida.model.entity.SessaoVotacao;
import br.com.cooperativa.decida.model.entity.Usuario;
import br.com.cooperativa.decida.model.entity.Voto;
import br.com.cooperativa.decida.model.enums.OpcaoDeVoto;
import br.com.cooperativa.decida.repository.PautaRepository;
import br.com.cooperativa.decida.repository.SessaoVotacaoRepository;
import br.com.cooperativa.decida.repository.UsuarioRepository;
import br.com.cooperativa.decida.repository.VotoRepository;
import br.com.cooperativa.decida.service.PautaService;
import br.com.cooperativa.decida.service.VotoService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class VotoServiceTests {
	private VotoService votoService;
	
	@MockBean
	private VotoRepository votoRepository;
	@MockBean
	private SessaoVotacaoRepository sessaoRepository;
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	public VotoServiceTests() {
		votoRepository = mock(VotoRepository.class);
		sessaoRepository = mock(SessaoVotacaoRepository.class);
		usuarioRepository = mock(UsuarioRepository.class);
		this.votoService = new VotoService(votoRepository, sessaoRepository, usuarioRepository);
	}
	
	@Test
	void deveInserirVoto_ParaDadosValidos() throws Exception {
		String cpfUsuario = "12345678901";
		int idPauta = 1;
		
		LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(5);
		Pauta pauta = new Pauta("Titulo", "Descricao");
		SessaoVotacao sessao = new SessaoVotacao(pauta, dataExpiracao);
		Usuario usuario = new Usuario("usuario@email.com", cpfUsuario);
		Voto voto = new Voto(OpcaoDeVoto.SIM, sessao, usuario);
		
		when(sessaoRepository.findById(idPauta)).thenReturn(Optional.of(sessao));
		when(usuarioRepository.findByCpf(cpfUsuario)).thenReturn(Optional.of(usuario));
		when(votoRepository.save(any(Voto.class))).thenReturn(voto);
		
		VotoDto dto = new VotoDto(OpcaoDeVoto.SIM, idPauta, cpfUsuario);
		VotoDto dtoCriado = votoService.votar(dto);
		
		assertNotNull(dtoCriado);
		assertEquals(OpcaoDeVoto.SIM, dtoCriado.getEscolha());
	}
}
