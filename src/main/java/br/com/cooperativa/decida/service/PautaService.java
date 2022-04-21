package br.com.cooperativa.decida.service;

import static br.com.cooperativa.decida.util.ConversorDeObjeto.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.cooperativa.decida.dto.PautaDto;
import br.com.cooperativa.decida.dto.ResultadoPautaDto;
import br.com.cooperativa.decida.dto.SessaoVotacaoDto;
import br.com.cooperativa.decida.modelo.Pauta;
import br.com.cooperativa.decida.modelo.SessaoVotacao;
import br.com.cooperativa.decida.repository.PautaRepository;
import br.com.cooperativa.decida.repository.SessaoVotacaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaService {
	private final PautaRepository repository;
	private final SessaoVotacaoRepository sessaoRepository;
	
	@Transactional
	public PautaDto cadastrar(PautaDto dto) {
		Pauta pauta = converter(dto, Pauta.class);
		pauta = repository.save(pauta);
		
		return converter(pauta, PautaDto.class);
	}

	public List<PautaDto> listar() {
		List<Pauta> pautas = (List<Pauta>)repository.findAll();
		
		return pautas.stream()
				.map(p -> converter(p, PautaDto.class))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void deletar(int id) {
		repository.deleteById(id);
	}
	
	@Transactional
	public SessaoVotacaoDto abrirSessao(SessaoVotacaoDto dto) {
		Pauta pauta = repository.getById(dto.getIdPauta());
		SessaoVotacao sessao = new SessaoVotacao(dto, pauta);
		
		sessao = sessaoRepository.save(sessao);
		
		return converter(sessao, SessaoVotacaoDto.class);
		
	}

	public ResultadoPautaDto obterResultado(Integer id) throws Exception {
		Pauta pauta = repository.findById(id).orElseThrow(Exception::new);
		
		return new ResultadoPautaDto(pauta);
	}
}
