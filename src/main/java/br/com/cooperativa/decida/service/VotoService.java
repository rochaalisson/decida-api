package br.com.cooperativa.decida.service;

import static br.com.cooperativa.decida.util.ConversorDeObjeto.converter;

import org.springframework.stereotype.Service;

import br.com.cooperativa.decida.dto.VotoDto;
import br.com.cooperativa.decida.modelo.SessaoVotacao;
import br.com.cooperativa.decida.modelo.Voto;
import br.com.cooperativa.decida.repository.SessaoVotacaoRepository;
import br.com.cooperativa.decida.repository.VotoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotoService {
	private final VotoRepository repository;
	private final SessaoVotacaoRepository sessaoRepository;
	
	public VotoDto votar(VotoDto dto) throws Exception {
		SessaoVotacao sessao = sessaoRepository.getById(dto.getIdPauta());
		
		if (sessao.isExpirada())
			throw new Exception();
		
		Voto voto = new Voto(dto, sessao);
		voto = repository.save(voto);
		
		return converter(voto, VotoDto.class);
	}
}
