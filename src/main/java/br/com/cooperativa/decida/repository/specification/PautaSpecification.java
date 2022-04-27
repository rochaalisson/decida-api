package br.com.cooperativa.decida.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.cooperativa.decida.model.entity.Pauta;

public final class PautaSpecification {
	public static Specification<Pauta> tituloContains(String titulo) {
		return (root, query, builder) -> builder.like(root.get("titulo"), "%"+titulo+"%");
	}
	
	public static Specification<Pauta> descricaoContains(String descricao) {
		return (root, query, builder) -> builder.like(root.get("descricao"), "%"+descricao+"%");
	}
}
