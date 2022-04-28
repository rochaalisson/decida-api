package br.com.cooperativa.decida.service;

import static br.com.cooperativa.decida.repository.specification.PautaSpecification.descricaoContains;
import static br.com.cooperativa.decida.repository.specification.PautaSpecification.tituloContains;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.cooperativa.decida.exception.PautaImutavelException;
import br.com.cooperativa.decida.exception.UsuarioNaoAutorizadoException;
import br.com.cooperativa.decida.model.dto.PautaDto;
import br.com.cooperativa.decida.model.dto.ResultadoPautaDto;
import br.com.cooperativa.decida.model.dto.SessaoVotacaoDto;
import br.com.cooperativa.decida.model.entity.Pauta;
import br.com.cooperativa.decida.model.entity.SessaoVotacao;
import br.com.cooperativa.decida.model.entity.Usuario;
import br.com.cooperativa.decida.repository.PautaRepository;
import br.com.cooperativa.decida.repository.SessaoVotacaoRepository;
import br.com.cooperativa.decida.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PautaService {
	private PautaRepository repository;
	private SessaoVotacaoRepository sessaoRepository;
	private UsuarioRepository usuarioRepository;

	@Transactional
	public PautaDto cadastrar(PautaDto dto, String cpfUsuario) {
		Usuario usuario = usuarioRepository.findByCpf(cpfUsuario)
				.orElseThrow(EntityNotFoundException::new);
		
		Pauta pauta = dto.toEntity(usuario);
		pauta = repository.save(pauta);

		return new PautaDto(pauta);
	}
	
	public List<PautaDto> listar(Optional<String> titulo, Optional<String> descricao) {
		List<Pauta> pautas = repository.findAll(Specification
				.where(titulo.isPresent() ? tituloContains(titulo.get()) : null)
				.and(descricao.isPresent() ? descricaoContains(descricao.get()) : null));

		return pautas.stream().map(PautaDto::new).collect(Collectors.toList());
	}

	@Transactional
	public PautaDto atualizar(PautaDto dto, String cpfUsuario) throws Exception {
		Pauta pauta = repository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

		if (!cpfUsuario.equals(pauta.getUsuario().getCpf()))
			throw new UsuarioNaoAutorizadoException();
		if (pauta.getSessaoVotacao() != null)
			throw new PautaImutavelException();

		pauta.atualizarDados(dto);

		return new PautaDto(pauta);
	}

	@Transactional
	public void deletar(int id, String cpfUsuario) throws Exception {
		Pauta pauta = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

		if (!cpfUsuario.equals(pauta.getUsuario().getCpf()))
			throw new UsuarioNaoAutorizadoException();
		if (pauta.getSessaoVotacao() != null)
			throw new PautaImutavelException();

		repository.delete(pauta);
	}

	@Transactional
	public SessaoVotacaoDto abrirSessao(SessaoVotacaoDto dto, String cpfUsuario) throws UsuarioNaoAutorizadoException {		
		Pauta pauta = repository.findById(dto.getIdPauta())
				.orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));
		
		if (!cpfUsuario.equals(pauta.getUsuario().getCpf()))
			throw new UsuarioNaoAutorizadoException();
		
		SessaoVotacao sessao = dto.toEntity(pauta);

		sessao = sessaoRepository.save(sessao);

		return new SessaoVotacaoDto(sessao);

	}

	public ResultadoPautaDto obterResultado(Integer id) {
		Pauta pauta = repository.findById(id).orElseThrow(EntityNotFoundException::new);

		return new ResultadoPautaDto(pauta);
	}
}
