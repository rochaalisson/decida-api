package br.com.cooperativa.decida.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.cooperativa.decida.dto.VotoDto;
import br.com.cooperativa.decida.exception.SessaoVotacaoExpiradaException;
import br.com.cooperativa.decida.modelo.SessaoVotacao;
import br.com.cooperativa.decida.modelo.Usuario;
import br.com.cooperativa.decida.modelo.Voto;
import br.com.cooperativa.decida.repository.SessaoVotacaoRepository;
import br.com.cooperativa.decida.repository.UsuarioRepository;
import br.com.cooperativa.decida.repository.VotoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotoService {
	private final VotoRepository repository;
	private final SessaoVotacaoRepository sessaoRepository;
	private final UsuarioRepository usuarioRepository;
	
	public VotoDto votar(VotoDto dto) throws Exception {
		SessaoVotacao sessao = sessaoRepository.findById(dto.getIdPauta())
				.orElseThrow(() -> new EntityNotFoundException("Sessão de votação não encontrada"));
		if (sessao.isExpirada())
			throw new SessaoVotacaoExpiradaException();
		
		Usuario usuario = usuarioRepository.findByCpf(dto.getCpfUsuario())
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
		
		Voto voto = dto.toEntity(sessao, usuario);
		voto = repository.save(voto);
		
		return new VotoDto(voto);
	}
}
