package br.com.alterdata.vendas.specification;

import br.com.alterdata.vendas.model.dto.FiltroDTO;
import br.com.alterdata.vendas.model.entity.Produto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

public class ProdutoSpecification {
    public static Specification<Produto> buildSpecification(final FiltroDTO filter) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (filter.getNome() != null) {
                predicate = builder.and(predicate, builder.like(root.get("nome"), "%" + filter.getNome() + "%"));
            }
            if (filter.getDescricao() != null) {
                predicate = builder.and(predicate, builder.like(root.get("descricao"), "%" + filter.getDescricao() + "%"));
            }
            if (filter.getCategoriaTitulo() != null) {
                Join<Object, Object> categoriaJoin = root.join("categoria");
                predicate = builder.and(predicate, builder.like(categoriaJoin.get("titulo"), "%" + filter.getCategoriaTitulo() + "%"));
            }
            if (filter.getCategoriaDescricao() != null) {
                Join<Object, Object> categoriaJoin = root.join("categoria");
                predicate = builder.and(predicate, builder.like(categoriaJoin.get("descricao"), "%" + filter.getCategoriaDescricao() + "%"));
            }
            return predicate;
        };
    }
}
