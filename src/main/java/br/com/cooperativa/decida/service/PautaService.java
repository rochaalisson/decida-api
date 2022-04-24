package br.com.cooperativa.decida.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaService {
	private final PautaRepository repository;
	private final SessaoVotacaoRepository sessaoRepository;
	private final UsuarioRepository usuarioRepository;

	@Transactional
	public PautaDto cadastrar(PautaDto dto) {
		Pauta pauta = dto.toEntity();
		pauta = repository.save(pauta);

		return new PautaDto(pauta);
	}

	public List<PautaDto> listar() {
		List<Pauta> pautas = repository.findAll();

		return pautas.stream().map(p -> new PautaDto(p)).collect(Collectors.toList());
	}

	@Transactional
	public PautaDto atualizar(PautaDto dto, String cpfUsuario) throws Exception {
		Usuario usuario = usuarioRepository.findByCpf(cpfUsuario)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
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
		Usuario usuario = usuarioRepository.findByCpf(cpfUsuario)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
		Pauta pauta = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

		if (!cpfUsuario.equals(pauta.getUsuario().getCpf()))
			throw new UsuarioNaoAutorizadoException();
		if (pauta.getSessaoVotacao() != null)
			throw new PautaImutavelException();

		repository.delete(pauta);
	}

	@Transactional
	public SessaoVotacaoDto abrirSessao(SessaoVotacaoDto dto) {
		Pauta pauta = repository.findById(dto.getIdPauta())
				.orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));
		SessaoVotacao sessao = dto.toEntity(pauta);

		sessao = sessaoRepository.save(sessao);

		return new SessaoVotacaoDto(sessao);

	}

	public ResultadoPautaDto obterResultado(Integer id) {
		Pauta pauta = repository.findById(id).orElseThrow(EntityNotFoundException::new);

		return new ResultadoPautaDto(pauta);
	}
}
